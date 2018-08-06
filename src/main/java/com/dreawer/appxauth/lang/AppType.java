package com.dreawer.appxauth.lang;

import java.io.Serializable;

/**
 * <CODE>AppType</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */
public enum AppType implements Serializable {

    /**
     * 微信
     */
    WXAPP,

    /**
     * 支付宝
     */
    ALIPAY;


    public static AppType get(String name) {
        for (AppType status : AppType.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
