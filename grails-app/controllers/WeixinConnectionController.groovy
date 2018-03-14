

import com.qq.weixin.mp.aes.WXBizMsgCrypt

class WeixinConnectionController {

    static allowedMethods = {index:"GET"};

    def index() {

        String sToken = "libosong";

        String sCorpID = "wx306af3f75da18e72";

        String sEncodingAESKey = "5qwPj6hjMXzIQNL8NbKmxx9dXYvAG1Xn4N1iFY7ghxo";

        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);

        if(request != null){

            String sVerifyMsgSig = URLDecoder.decode(request.getParameter("msg_signature"),"utf-8");

            String sVerifyTimeStamp = URLDecoder.decode(request.getParameter("timestamp"),"utf-8");

            String sVerifyNonce = URLDecoder.decode(request.getParameter("nonce"),"utf-8");

            String sVerifyEchoStr = URLDecoder.decode(request.getParameter("echostr"),"utf-8");

            PrintWriter out = response.getWriter();

            String sEchoStr; //需要返回的明文

            try {

                sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,sVerifyNonce, sVerifyEchoStr);

                System.out.println("verifyurl echostr: " + sEchoStr);

                // 验证URL成功，将sEchoStr返回

                out.print(sEchoStr);

                out.close();

                out = null;

            } catch (Exception e) {

                //验证URL失败，错误原因请查看异常

                e.printStackTrace();

            }
        }
        //redirect(action:'connection' ,params: params);
    }
}
