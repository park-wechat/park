package park

import grails.transaction.Transactional
import org.grails.jaxrs.provider.DomainObjectNotFoundException

@Transactional
class HotelService {

    def serviceMethod() {

    }

    def readAllRooms(){
        def list = Room.findAll()
        if(list == null){
            throw new DomainObjectNotFoundException(Room.class, "get rooms")
        }
        list
    }

    def readRoom(Long id){
        def room = Room.get(id)
        if(room == null){
            throw new DomainObjectNotFoundException(Room.class, "get room info")
        }
        room
    }
}
