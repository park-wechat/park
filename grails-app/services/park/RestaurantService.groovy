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
            throw new DomainObjectNotFoundException(Park.class, "get classify list")
        }
        list
    }

    def getRestaurantList(){
        def list = Restaurant.findAll()
        if(list == null){
            throw new DomainObjectNotFoundException(Park.class, "get Restaurant list")
        }
        list
    }


    def getMenuList(){
        def list = Menu.findAll()
        if(list == null){
            throw new DomainObjectNotFoundException(Park.class, "get Restaurant list")
        }
        list
    }

    def listClassifyMenu(){
        def list = Menu.findAllByClassifyName()
        if(list == null){
            throw new DomainObjectNotFoundException(Park.class, "get Restaurant list")
        }
        list
    }


    def getInfoMenu(String menuName){
        def list = Menu.findAllByMenuName(menuName)
        if(list == null){
            throw new DomainObjectNotFoundException(Park.class, "get Restaurant list")
        }
        list
    }
}
