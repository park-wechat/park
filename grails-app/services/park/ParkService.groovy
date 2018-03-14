package park

import grails.transaction.Transactional
import org.grails.jaxrs.provider.DomainObjectNotFoundException

@Transactional
class ParkService {

    def serviceMethod() {

    }

    def readList(){
        def list = Park.findAll();
        if(list == null){
            throw new DomainObjectNotFoundException(Park.class, "get list")
        }
        list
    }

    def read(parkName) {
        def obj = Park.findAllByParkName(parkName)
        if (obj == null) {
            throw new DomainObjectNotFoundException(Park.class, parkName)
        }
        obj
    }
}
