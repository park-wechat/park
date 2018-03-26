package park

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

import static org.grails.jaxrs.response.Responses.ok

@Path('/api/hotel')
@Consumes(['application/json'])
@Produces(['application/json'])
class HotelResource {
    def hotelService
    @GET
    @Produces('text/plain')
    String getHotelRepresentation() {
        'Hotel'
    }

    @GET
    @Path('/room/list')
    Response getRooms() {
        ok hotelService.readAllRooms()
    }

    @GET
    @Path('/room/info/{roomId}')
    Response getRoomInfo(@PathParam('roomId') Long roomId) {
        ok hotelService.readRoom(roomId)
    }
}
