package park

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

import static org.grails.jaxrs.response.Responses.ok

@Path('/api/restaurant')
@Consumes(['application/json'])
@Produces(['application/json'])
class RestaurantResource {
    def restaurantService

    @GET
    @Path('/classifies')
    Response getClassifyn() {
        ok restaurantService.getClassifies()
    }

    @GET
    @Path('/list')
    Response getRestaurantList() {
        ok restaurantService.getRestaurantList()
    }

    @GET
    @Path('/listMenu')
    Response getMenuList() {
        ok restaurantService.getMenuList()
    }

    @GET
    @Path('/listClassifyMenu/{classifyName}')
    Response listClassifyMenu(@PathParam('classifyName') String classifyName) {
        ok restaurantService.listClassifyMenu(classifyName)
    }

    @GET
    @Path('/infoMenu/{menuName}')
    Response getInfoMenu(@PathParam('menuName') String menuName) {
        ok restaurantService.getInfoMenu(menuName)
    }
}
