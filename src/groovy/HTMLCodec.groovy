/**
 * Created by Administrator on 2018/1/14.
 */
class HTMLCodec {
    static decode = { theTarget ->
        org.springframework.web.util.HtmlUtils.htmlUnescape(theTarget.toString())
    }
}
