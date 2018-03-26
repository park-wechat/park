package park

import com.github.wxpay.sdk.WXPayUtil
import com.google.common.collect.Maps
import grails.converters.JSON
import org.json.JSONObject
import park.utils.AuthToken
import park.utils.GsonUtils
import park.utils.WeChatConfig
import park.utils.WeChatConstant
import park.utils.WeChatUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response

import static org.grails.jaxrs.response.Responses.ok

@Path('/api/weixin')
class WeiXinPayResource {
    def weChatPayService
    def orderService
    def restaurantService
    @GET
    @Produces('text/plain')
    String getWeiXinPayRepresentation() {
        'WeiXinPay'
    }

    @GET
    @Produces('text/html')
    @Path('/payFood/')
    String getParkInfo(@QueryParam('code')String code, @QueryParam('state')String state,
                         @Context HttpServletRequest request) { //, Model model
        Map<String,Object> result = new HashMap<>();
        Menu menu = restaurantService.getMenu(Long.parseLong(state))
        if(menu == null){
            result.put("success",false);
            result.put("data","菜品不存在")
            ok(result);
            return;
        }

        //通过code获取网页授权access_token
        AuthToken authToken = WeChatUtils.getTokenByAuthCode(code);
        if(authToken != null){
            System.out.println(authToken.access_token)
        }

        //构建微信统一下单需要的参数
        Map<String,Object> map = Maps.newHashMap();
        map.put("openId",authToken.getOpenid());//用户标识openId
        map.put("remoteIp",request.getRemoteAddr());//请求Ip地址
        //调用统一下单service
        WeiXinOrder weiXinOrder = doWeixinOrder(menu);
        Map<String,Object> resultMap = weChatPayService.unifiedOrder(weiXinOrder,map);
        String returnCode = (String) resultMap.get("return_code");//通信标识
        String resultCode = (String) resultMap.get("result_code");//交易标识
        //只有当returnCode与resultCode均返回“success”，才代表微信支付统一下单成功
        if (WeChatConstant.RETURN_SUCCESS.equals(resultCode)&&WeChatConstant.RETURN_SUCCESS.equals(returnCode)){
            String appId = (String) resultMap.get("appid");//微信公众号AppId
            String timeStamp = WeChatUtils.getTimeStamp();//当前时间戳
            String prepayId = "prepay_id="+resultMap.get("prepay_id");//统一下单返回的预支付id
            String nonceStr = WeChatUtils.getRandomStr(20);//不长于32位的随机字符串
            SortedMap<String,String> signMap = Maps.newTreeMap();//自然升序map
            signMap.put("appId",appId);
            signMap.put("timeStamp",timeStamp);
            signMap.put("nonceStr",nonceStr);
            signMap.put("package",prepayId);
            signMap.put("signType","MD5");

//            Map<String,String> model = Maps.newTreeMap();
//            model.put("appId",appId);
//            model.put("timeStamp",timeStamp);
//            model.put("nonceStr",nonceStr);
//            model.put("package",prepayId);
            signMap.put("paySign",WXPayUtil.generateSignature(signMap, WeChatConfig.getInstance().getKey()));//获取签名
            System.out.println("paySign----->" + WXPayUtil.generateSignature(signMap, WeChatConfig.getInstance().getKey()))
//            result.put("success",new Boolean(true));
//            result.put("data",model);
            return  payHtml(signMap);
        }else {
            result.put("success",new Boolean(false));
            result.put("data","微信下单失败");//order.getOrderNumber()
            System.out.println("微信统一下单失败,订单编号:"+weiXinOrder.getOrderNumber()+",失败原因:"+resultMap.get("err_code_des"));
            //return Response.ok(GsonUtils.getGson().toJson(result)).build()
        }
        //将支付需要参数返回至页面，采用h5方式调用支付接口
//        return "/mobile/order/h5Pay";
//        ok result
    }

    @GET
    @Path('/notify/')
    Response notify(@Context HttpServletRequest request) { //, Model model
        weChatPayService.notifyResult(request)
    }

    private WeiXinOrder doWeixinOrder(Menu menu){
        WeiXinOrder order = new WeiXinOrder()
        String orderNum = UUID.randomUUID().mostSignificantBits + "";
        orderNum.substring(1);
        order.setOrderNumber(orderNum)
        order.setOrderContent(menu.getMenuName())
//        order.setSumFee(1)
        order
    }

    private String payHtml(Map<String,Object> model){

        String html = "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover\">" +
                "<title>确认支付</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "</body>\n" +
                "<script>\n" +
                "\n" +
                "    function onBridgeReady(){\n" +
                "        WeixinJSBridge.invoke(\n" +
                "                'getBrandWCPayRequest', {\n" +
                "                    \"appId\":\""+model.get("appId")+"\",\n" +
                "                    \"timeStamp\":\""+model.get("timeStamp")+"\",\n" +
                "                    \"nonceStr\":\""+model.get("nonceStr")+"\",\n" +
                "                    \"package\":\""+model.get("package")+"\",\n" +
                "                    \"signType\":\"MD5\",\n" +
                "                    \"paySign\":\""+model.get("paySign")+"\"\n" +
                "                },\n" +
                "            function(res){\n" +
                "                alert(res) \n" +
                "                alert(res.err_msg) \n" +
                "                if(res.err_msg == \"get_brand_wcpay_request:ok\" ) {\n" +
                "                    location.href=\"支付成功返回商家自定义页面\";\n" +
                "                }else {//这里支付失败和支付取消统一处理\n" +
                "                    alert(\"支付取消\");\n" +
                "                    location.href=\"http://\";\n" +
                "                }\n" +
                "            }\n" +
                "        );\n" +
                "    }\n" +
                "\n" +
//                "    \$(document).ready(function () {\n" +
                "        if (typeof WeixinJSBridge == \"undefined\"){\n" +
                "            if (document.addEventListener){\n" +
                "                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);\n" +
                "            }else if (document.attachEvent){\n" +
                "                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);\n" +
                "                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);\n" +
                "            }\n" +
                "        }else {\n" +
                "            onBridgeReady();\n" +
                "        }\n" +
//                "    });\n" +
                "</script>\n" +
                "</html>"
        System.out.println(html)
        return html;
    }
}
