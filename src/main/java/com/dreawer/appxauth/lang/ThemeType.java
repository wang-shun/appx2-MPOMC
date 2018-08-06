package com.dreawer.appxauth.lang;

public enum ThemeType {

    /**
     * 预览
     **/
    PREVIEW,

    /**
     * 定制
     **/
    CUSTOM;

    /**
     * 获取类目类型
     *
     * @param name
     * @return 枚举对象
     */
    private static ThemeType get(String name) {
        for (ThemeType type : ThemeType.values()) {
            if (type.toString().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
