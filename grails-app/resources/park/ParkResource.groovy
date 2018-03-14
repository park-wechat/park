package park
import static org.grails.jaxrs.response.Responses.*
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path('/api/park')
@Consumes(['application/json'])
@Produces(['application/json'])
class ParkResource {
    def parkService

    @GET
    @Path('/parkList')
    Response getParkList() {
        ok parkService.readList()
    }

    @GET
    @Path('/parkInfo/{parkName}')
    Response getParkInfo(@PathParam('parkName') String parkName) {
        ok parkService.read(parkName)
    }
}
