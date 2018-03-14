package park

import grails.converters.JSON
import grails.transaction.Transactional
import org.imgscalr.Scalr
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class RestaurantController {

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort= 'id'
        params.order='desc'
        respond Restaurant.list(), model:[restaurantInstanceCount: Restaurant.count()]
    }

    def create(){
        respond new Restaurant();
    }

    def edit(Restaurant restaurant){
        [restaurant:restaurant]
    }

    @Transactional
    def save(Restaurant restaurant){
        if (restaurant == null) {
            notFound()
            return
        }

        if (restaurant.hasErrors()) {
            respond restaurant.errors, view: 'create'
            return
        }
        restaurant.createDate = new Date();
        restaurant.save flush: true

        if(session.getAttribute("restaurant") != null){
            session.removeAttribute("restaurant")
        }
        session.setAttribute("restaurant",restaurant)
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'newsNotice.label', default: ''), restaurant.restaurantName])
                redirect action: "index", method: "GET"
            }
            '*' { respond restaurant, [status: CREATED] }
        }
    }

    @Transactional
    def delete(Restaurant restaurant){
        if (restaurant == null) {
            notFound()
            return
        }

        List<Menu> menus = Menu.findAllByRestaurant_id(restaurant.id);
        for(Menu menu : menus){
            def uploader = grailsApplication.config.uploadr.defaultUploadPath
            def path = new File("${uploader}/imageUploadr/"+ menu.menuUrl);
            if(path.exists()){
                if(!path.deleteDir()){
                    System.out.println("can not delete file, path=["+path+"]")
                }
            }
        }
        Menu.executeUpdate("delete Menu m where m.restaurant_id = :restaurant_id"
                ,[restaurant_id:restaurant.id])

        restaurant.delete flush: true
        if(session.getAttribute("restaurant") != null){
            session.removeAttribute("restaurant")
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message',
                        args: [message(code: 'NewsNotice.label', default: ''), restaurant.restaurantName])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    @Transactional
    def update(Restaurant restaurant){
        if (restaurant == null) {
            notFound()
            return
        }

        if (restaurant.hasErrors()) {
            respond restaurant.errors, view: 'edit'
            return
        }
        restaurant.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message',
                        args: [message(code: 'NewsNotice.label', default: ''), restaurant.restaurantName])
                redirect action: "index", method: "GET"
            }
            '*' { respond restaurant, [status: OK] }
        }
    }

    def listMenu(Restaurant restaurant){
        if(restaurant == null){
            restaurant = (Restaurant)session.getAttribute("restaurant")
        }

        if(session.getAttribute("restaurant") == null){
            session.setAttribute("restaurant",restaurant)
        }

        params.max = 10
        params.sort= 'id'
        params.order='desc'
        List<Menu> menus = Menu.findAllByRestaurant_id(restaurant.id);
        if(null == menus){
            notFound();
            return;
        }
        respond menus, model:[menuInstanceCount: menus.size()]
    }

    def createMenu(Restaurant restaurant){
        if(restaurant == null){
            restaurant = (Restaurant)session.getAttribute("restaurant")
        }
        Menu menu= new Menu()
        respond menu, model:[restaurant:restaurant];
    }

    @Transactional
    def saveMenu(Menu menu){
        Restaurant restaurant = (Restaurant)session.getAttribute("restaurant")
        if (menu == null) {
            notFound()
            return
        }

        if (menu.hasErrors()) {
            respond menu.errors, view: 'createMenu'
            return
        }

        List<Menu> tmp = Menu.findAllByMenuName(menu.menuName);
        if(tmp !=null && tmp.size() > 0){
            respond "not ${menu.menuName} existed", view: 'createMenu'
            return
        }

        menu.restaurant_id = restaurant.id;
        menu.save flush: true

        def uploader = grailsApplication.config.uploadr.defaultUploadPath
        def path = new File("${uploader}/imageUploadr/"+ menu.menuUrl)
        boolean mainPicture = false;
        for(File file : path.listFiles()){
            MenuUrls menuUrls = new MenuUrls();
            menuUrls.fileName = file.name
            menuUrls.menuUrl = menu.menuUrl;
            menuUrls.menu_id = menu.id;
            Long pictureId = UUID.randomUUID().leastSignificantBits;
            menuUrls.pirtureId = pictureId;
            def pictureUrl = createLink(controller: 'image',action: 'picture',params: pictureId)
            menuUrls.url = pictureUrl + "?pictureId="+pictureId
            if(!mainPicture){
                menu.menuPicture =  menuUrls.url;
                mainPicture = true;
            }
            menuUrls.save(flush: true)
        }
        menu.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message',
                        args: [message(code: 'newsNotice.label', default: ''), menu.menuName])
                redirect action: "listMenu", method: "GET"
            }
            '*' { respond menu, [status: CREATED] }
        }
    }

    def editMenu(Menu menu){
        [menu:menu]
    }

    @Transactional
    def updateMenu(Menu menu){
        if (menu == null) {
            notFound()
            return
        }

        if (menu.hasErrors()) {
            respond menu.errors, view: 'editMenu'
            return
        }

        List<Menu> tmp = Menu.findAllByMenuName(menu.menuName);
        if(tmp !=null && tmp.size() == 0){
            respond "not ${menu.menuName} existed", view: 'listMenu'
            return
        }
        Menu saveMenu = tmp.get(0);
        saveMenu.menuName = menu.menuName;
        saveMenu.price = menu.price;
        saveMenu.sale = menu.sale;
        saveMenu.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message',
                        args: [message(code: 'NewsNotice.label', default: ''), saveMenu.menuName])
                redirect action: "listMenu", method: "GET"
            }
            '*' { respond saveMenu, [status: OK] }
        }
    }

    @Transactional
    def deleteMenu(Menu menu){
        if (menu == null) {
            notFound()
            return
        }

        def uploader = grailsApplication.config.uploadr.defaultUploadPath
        def path = new File("${uploader}/imageUploadr/"+ menu.menuUrl);
        if(path.exists()){
            if(!path.deleteDir()){
                System.out.println("can not delete file, path=["+path+"]")
            }
        }
        Menu.executeUpdate("delete Menu m where m.menuName = :menuName"
                ,[menuName:menu.menuName])
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message',
                        args: [message(code: 'NewsNotice.label', default: ''), menu.menuName])
                redirect action: "listMenu", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message',
                        args: [message(code: 'newsNotice.label', default: 'error'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
