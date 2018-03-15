package park

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces

@Path('/api/resource')
class ResourceResource {
    def parkService
    def host_assets = "http://localhost:9000/park/assets/"
    @GET
    @Produces('text/html')
    @Path('/parkDetail/{parkId}')
    String getResourceRepresentation(@PathParam('parkId') String parkId) {

        String html = "";
        Park park = Park.get(parkId)
        html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "    \t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover\">\n" +
                "\t\t<title>景点详情页面</title>\n" +
                "    \t<link rel=\"stylesheet\" href=\""+host_assets+"weui.min.css\"/>\n" +
                "    \t<link rel=\"stylesheet\" href=\""+host_assets+"style.css\"/>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<div class=\"weui-panel__bd\">\n" +
                "\t\t\t<article class=\"weui-article\">\n" +
                "\t\t\t\t<section>\n" +
                "\t\t\t\t\t<h2 class=\"title\">"+park.parkName+"</h2>\n" +
                "\t\t\t\t\t<h3><span class=\"font-20\">·</span>景点介绍</h3>\n" +
                "\t\t\t\t\t<section class=\"weui-media-box\">\n" +
                "\t\t\t\t\t\t"+park.parkContent+
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<img class=\"weui-media-box__thumb border-radius5\" src=\"img/51lvcn3.jpg\" alt=\"\">\n" +
                "\t\t\t\t\t</section>\n" +
                "\t\t\t\t</section>\n" +
                "\t\t\t</article>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "\n" +
                "</html>"
        html
    }
}
