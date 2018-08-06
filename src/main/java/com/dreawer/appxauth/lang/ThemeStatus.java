package com.dreawer.appxauth.lang;

public enum ThemeStatus {

    /**
     * 默认
     **/
    DEFAULT,

    /**
     * 已删除
     **/
    DELETED;

    /**
     * 获取类目状态
     *
     * @param name
     * @return 枚举对象
     */
    private static ThemeStatus get(String name) {
        for (ThemeStatus status : ThemeStatus.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
