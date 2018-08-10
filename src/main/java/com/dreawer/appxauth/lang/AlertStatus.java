package com.dreawer.appxauth.lang;

/**
 * <CODE>AlertStatus</CODE>
 *
 * @author fenrir
 * @Date 18-8-10
 */
public enum AlertStatus {

    /**
     * 黄色预警
     **/
    YELLOW,

    /**
     * 红色预警
     **/
    RED;

    /**
     * 获取性别
     *
     * @param name
     * @return 枚举对象
     */
    public static AlertStatus get(String name) {
        for (AlertStatus status : AlertStatus.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
