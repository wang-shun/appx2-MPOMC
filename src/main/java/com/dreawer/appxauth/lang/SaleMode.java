package com.dreawer.appxauth.lang;

public enum SaleMode {

    /**
     * 默认
     */
    DEFAULT,

    /**
     * 预售
     */
    ADVANCE;

    /**
     * 获取销售模式
     *
     * @param name
     * @return 枚举对象
     */
    private static SaleMode get(String name) {
        for (SaleMode mode : SaleMode.values()) {
            if (mode.toString().equalsIgnoreCase(name)) {
                return mode;
            }
        }
        return null;
    }
}
