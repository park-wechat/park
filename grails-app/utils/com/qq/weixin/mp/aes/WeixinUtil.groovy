package com.qq.weixin.mp.aes

/**
 * Created by Administrator on 2014/12/4.
 */
class WeixinUtil {
    public static String getFileEndWitsh(String headField){
        String []str = headField.split(".");
        if(str.length() > 1){
            return str[1];
        }else{
            return "";
        }
    }
}
