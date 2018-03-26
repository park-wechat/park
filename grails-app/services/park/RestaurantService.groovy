package park

import grails.transaction.Transactional
import org.grails.jaxrs.provider.DomainObjectNotFoundException

@Transactional
class RestaurantService {

    def serviceMethod() {

    }

    def getClassifies(){
        def list = Classify.findAll()
        if(list == null){
            throw new DomainObjectNotFoundException(Classify.class, "get classify list")
        }
        list
    }

    def getRestaurantList(){
        def list = Restaurant.findAll()
        if(list == null){
            throw new DomainObjectNotFoundException(Restaurant.class, "get Restaurant list")
        }
        list
    }


    def getMenuList(){
        def list = Menu.findAll()
        if(list == null){
            throw new DomainObjectNotFoundException(Menu.class, "get menu list")
        }
        list
    }

    def listClassifyMenu(String classifyName){
        def list = Menu.findAllByClassifyName(classifyName)
        if(list == null){
            throw new DomainObjectNotFoundException(Menu.class, "get menu list")
        }
        list
    }

    def getMenu(Long id){
        def menu = Menu.get(id)
        if(menu == null){
            throw new DomainObjectNotFoundException(Park.class, "get menu")
        }
        menu
    }

    def getInfoMenu(String menuName){
        def list = Menu.findAllByMenuName(menuName)
        if(list == null){
            throw new DomainObjectNotFoundException(Park.class, "get Restaurant list")
        }
        list
    }
}
