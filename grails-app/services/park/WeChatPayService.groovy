package park

import com.github.wxpay.sdk.WXPay
import grails.transaction.Transactional
import org.apache.commons.lang.StringUtils
import park.utils.MD5Util
import park.utils.WeChatConfig
import park.utils.WeChatUtils
import park.utils.WxPaySendData

import javax.servlet.http.HttpServletRequest

@Transactional
class WeChatPayService {

/**
 * 微信支付统一下单
 **/
    public Map<String, Object> unifiedOrder(WeiXinOrder order, Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            WxPaySendData paySendData = new WxPaySendData();
            //构建微信支付请求参数集合
            paySendData.setAppId(WeChatConfig.getInstance().getAppID());
            paySendData.setAttach("微信订单支付:" + order.getOrderNumber()); //
            paySendData.setBody(order.getOrderContent()); //订单内容order.getOrderContent()
            paySendData.setMchId(WeChatConfig.getInstance().getMchID());
            paySendData.setNonceStr(WeChatUtils.getRandomStr(32));
            paySendData.setNotifyUrl(WeChatConfig.NOTIFY_URL);
            paySendData.setDeviceInfo("WEB");
            paySendData.setOutTradeNo(order.getOrderNumber());
            paySendData.setTotalFee(1);//order.getSumFee()
            paySendData.setTradeType(WeChatConfig.TRADE_TYPE_JSAPI);
            paySendData.setSpBillCreateIp((String) map.get("remoteIp"));
            paySendData.setOpenId((String) map.get("openId"));
            //将参数拼成map,生产签名
            SortedMap<String, Object> params = buildParamMap(paySendData);
            paySendData.setSign(WeChatUtils.getSign(params));

            //将请求参数对象转换成xml
            Map<String,String> data = new HashMap<>();
            data.put("appid",paySendData.getAppId());
            data.put("mch_id",paySendData.getMchId());
            data.put("device_info",paySendData.getDeviceInfo());
            data.put("nonce_str",paySendData.getNonceStr());
            data.put("sign",paySendData.getSign());
            data.put("body",paySendData.getBody());
            data.put("detail",paySendData.getDetail());
            data.put("attach",paySendData.getAttach());
            data.put("out_trade_no",paySendData.getOutTradeNo());
            data.put("fee_type",paySendData.getFeeType());
            data.put("total_fee",paySendData.getTotalFee()+"");
            data.put("trade_type",paySendData.getTradeType());
            data.put("notify_url",paySendData.getNotifyUrl());
            data.put("spbill_create_ip",paySendData.getSpBillCreateIp());
            data.put("time_start",paySendData.getTimeStart());
            data.put("time_expire",paySendData.getTimeExpire());
            data.put("openid",paySendData.getOpenId());
            data.put("goods_tag",paySendData.getGoodsTag());
            data.put("product_id",paySendData.getProductId());
            data.put("limit_pay",paySendData.getLimitPay());

            System.out.println("openid------------>" + data.get("openid"))
            WXPay wxPay = new WXPay(WeChatConfig.getInstance());
            resultMap = wxPay.unifiedOrder(data);
            System.out.println("response="+resultMap)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return resultMap;
    }

    /**
     * 修改订单状态，获取微信回调结果
     * @param request
     * @return
     */
    public String notifyResult(HttpServletRequest request){
        String inputLine;
        String notifyXml = "";
        String resXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null){
                notifyXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("接收到的xml：" + notifyXml);
        if(StringUtils.isEmpty(notifyXml)){
            System.out.println("xml为空：");
        }

        String appid = getXmlPara(notifyXml,"appid");;
        String bank_type = getXmlPara(notifyXml,"bank_type");
        String cash_fee = getXmlPara(notifyXml,"cash_fee");
        String fee_type = getXmlPara(notifyXml,"fee_type");
        String is_subscribe = getXmlPara(notifyXml,"is_subscribe");
        String mch_id = getXmlPara(notifyXml,"mch_id");
        String nonce_str = getXmlPara(notifyXml,"nonce_str");
        String openid = getXmlPara(notifyXml,"openid");
        String out_trade_no = getXmlPara(notifyXml,"out_trade_no");
        String result_code = getXmlPara(notifyXml,"result_code");
        String return_code = getXmlPara(notifyXml,"return_code");
        String sign = getXmlPara(notifyXml,"sign");
        String time_end = getXmlPara(notifyXml,"time_end");
        String total_fee = getXmlPara(notifyXml,"total_fee");
        String trade_type = getXmlPara(notifyXml,"trade_type");
        String transaction_id = getXmlPara(notifyXml,"transaction_id");

        //根据返回xml计算本地签名
        String localSign =
                "appid=" + appid +
                        "&bank_type=" + bank_type +
                        "&cash_fee=" + cash_fee +
                        "&fee_type=" + fee_type +
                        "&is_subscribe=" + is_subscribe +
                        "&mch_id=" + mch_id +
                        "&nonce_str=" + nonce_str +
                        "&openid=" + openid +
                        "&out_trade_no=" + out_trade_no +
                        "&result_code=" + result_code +
                        "&return_code=" + return_code +
                        "&time_end=" + time_end +
                        "&total_fee=" + total_fee +
                        "&trade_type=" + trade_type +
                        "&transaction_id=" + transaction_id +
                        "&key=" + WeChatConfig.KEY;//注意这里的参数要根据ASCII码 排序
        localSign = MD5Util.MD5Encode(localSign,"").toUpperCase();//将数据MD5加密

        System.out.println("本地签名是：" + localSign);
        System.out.println("微信支付签名是：" + sign);

        //本地计算签名与微信返回签名不同||返回结果为不成功
        if(!sign.equals(localSign) || !"SUCCESS".equals(result_code) || !"SUCCESS".equals(return_code)){
            System.out.println("验证签名失败或返回错误结果码");
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";
        }else{
            System.out.println("公众号支付成功，out_trade_no(订单号)为：" + out_trade_no);
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        }
        return resXml;
    }

    /**
     * 解析XML 获得名称为para的参数值
     * @param xml
     * @param para
     * @return
     */
    public static String getXmlPara(String xml,String para){
        int start = xml.indexOf("<"+para+">");
        int end = xml.indexOf("</"+para+">");

        if(start < 0 && end < 0){
            return null;
        }
        return xml.substring(start + ("<"+para+">").length(),end).replace("<![CDATA[","").replace("]]>","");
    }

    /**
     * 构建统一下单参数map 用于生成签名
     * @param data
     * @return SortedMap < String , Object >
     */
    private static SortedMap<String, Object> buildParamMap(WxPaySendData data) {
        SortedMap<String, Object> paramters = new TreeMap<String, Object>();
        if (null != data) {
            if (StringUtils.isNotEmpty(data.getAppId())) {
                paramters.put("appid", data.getAppId());
            }
            if (StringUtils.isNotEmpty(data.getAttach())) {
                paramters.put("attach", data.getAttach());
            }
            if (StringUtils.isNotEmpty(data.getBody())) {
                paramters.put("body", data.getBody());
            }
            if (StringUtils.isNotEmpty(data.getDetail())) {
                paramters.put("detail", data.getDetail());
            }
            if (StringUtils.isNotEmpty(data.getDeviceInfo())) {
                paramters.put("device_info", data.getDeviceInfo());
            }
            if (StringUtils.isNotEmpty(data.getFeeType())) {
                paramters.put("fee_type", data.getFeeType());
            }
            if (StringUtils.isNotEmpty(data.getGoodsTag())) {
                paramters.put("goods_tag", data.getGoodsTag());
            }
            if (StringUtils.isNotEmpty(data.getLimitPay())) {
                paramters.put("limit_pay", data.getLimitPay());
            }
            if (StringUtils.isNotEmpty(data.getMchId())) {
                paramters.put("mch_id", data.getMchId());
            }
            if (StringUtils.isNotEmpty(data.getNonceStr())) {
                paramters.put("nonce_str", data.getNonceStr());
            }
            if (StringUtils.isNotEmpty(data.getNotifyUrl())) {
                paramters.put("notify_url", data.getNotifyUrl());
            }
            if (StringUtils.isNotEmpty(data.getOpenId())) {
                paramters.put("openid", data.getOpenId());
            }
            if (StringUtils.isNotEmpty(data.getOutTradeNo())) {
                paramters.put("out_trade_no", data.getOutTradeNo());
            }
            if (StringUtils.isNotEmpty(data.getSign())) {
                paramters.put("sign", data.getSign());
            }
            if (StringUtils.isNotEmpty(data.getSpBillCreateIp())) {
                paramters.put("spbill_create_ip", data.getSpBillCreateIp());
            }
            if (StringUtils.isNotEmpty(data.getTimeStart())) {
                paramters.put("time_start", data.getTimeStart());
            }
            if (StringUtils.isNotEmpty(data.getTimeExpire())) {
                paramters.put("time_expire", data.getTimeExpire());
            }
            if (StringUtils.isNotEmpty(data.getProductId())) {
                paramters.put("product_id", data.getProductId());
            }
            if (data.getTotalFee() > 0) {
                paramters.put("total_fee", data.getTotalFee());
            }
            if (StringUtils.isNotEmpty(data.getTradeType())) {
                paramters.put("trade_type", data.getTradeType());
            }
            //申请退款参数
            if (StringUtils.isNotEmpty(data.getTransactionId())) {
                paramters.put("transaction_id", data.getTransactionId());
            }
            if (StringUtils.isNotEmpty(data.getOutRefundNo())) {
                paramters.put("out_refund_no", data.getOutRefundNo());
            }
            if (StringUtils.isNotEmpty(data.getOpUserId())) {
                paramters.put("op_user_id", data.getOpUserId());
            }
            if (StringUtils.isNotEmpty(data.getRefundFeeType())) {
                paramters.put("refund_fee_type", data.getRefundFeeType());
            }
            if (null != data.getRefundFee() && data.getRefundFee() > 0) {
                paramters.put("refund_fee", data.getRefundFee());
            }
        }
        return paramters;
    }

}
