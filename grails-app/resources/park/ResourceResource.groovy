package park

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path('/api/resource')
class ResourceResource {

    @GET
    @Produces('text/plain')
    String getResourceRepresentation() {
        'Resource'
    }
}
