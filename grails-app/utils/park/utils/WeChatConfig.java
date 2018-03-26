package park.utils;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/19.
 */
public class WeChatConfig extends WXPayConfig{
    private byte[] certData;
    private static WeChatConfig INSTANCE;
//    /**公众号AppId*/
//    public static final String APP_ID = WeiAPI.APP_ID;
//
//    /**公众号AppSecret*/
//    public static final String APP_SECRET = WeiAPI.APP_SECRET;
//
//    /**微信支付商户号*/
//    public static final String MCH_ID = "1499797652";

    /**微信支付API秘钥*/
//    public static final String KEY = "abc4PAvYlJ5Y0ZxMRWqb0453nbLkl0Kb";

    /**微信支付api证书路径*/
//    public static final String CERT_PATH = "***/apiclient_cert.p12";

    /**微信统一下单url*/
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**微信申请退款url*/
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**微信支付通知url*/
    public static final String NOTIFY_URL = "http://www.lszz.cc/api/weixin/notify";

    /**微信交易类型:公众号支付*/
    public static final String TRADE_TYPE_JSAPI = "JSAPI";

    /**微信交易类型:原生扫码支付*/
    public static final String TRADE_TYPE_NATIVE = "NATIVE";

    /**微信甲乙类型:APP支付*/
    public static final String TRADE_TYPE_APP = "APP";


    private WeChatConfig() {
    }

    public static WeChatConfig getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WeChatConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WeChatConfig();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String getAppID() {
        return "wx646f701bf7413934";
    }

    @Override
    public String getMchID() {
        return "1499797652";
    }

    @Override
    public String getKey() {
        return "abc4PAvYlJ5Y0ZxMRWqb0453nbLkl0Kb";
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public InputStream getCertStream(){
        return null;
    }

    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    public boolean shouldAutoReport() {
        return super.shouldAutoReport();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportQueueMaxSize() {
        return super.getReportQueueMaxSize();
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
