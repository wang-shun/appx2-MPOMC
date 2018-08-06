package com.dreawer.appxauth.lang;


public enum Platform {

    /**
     * 微信
     **/
    WECHAT,

    /**
     * 支付宝
     **/
    ALIPAY,

    /**
     * 同时兼容
     **/
    BOTH;

    /**
     * 获取支持平台
     *
     * @param name
     * @return 枚举对象
     */
    public static Platform get(String name) {
        for (Platform platform : Platform.values()) {
            if (platform.toString().equalsIgnoreCase(name)) {
                return platform;
            }
        }
        return null;
    }
}
