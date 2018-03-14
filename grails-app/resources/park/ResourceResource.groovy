package park

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

import static org.grails.jaxrs.response.Responses.ok

@Path('/api/guest')
@Consumes(['application/xml', 'application/json'])
@Produces(['application/xml', 'application/json'])
class ResourceResource {
    def parkService

    @GET
    @Path("/park({parkName})")
    Response readAllByUser(@PathParam('parkName') String parkName) {
        def params = [:]
        params.sort= 'id'
        params.order='desc'
        ok parkService.read(parkName)
    }
}
