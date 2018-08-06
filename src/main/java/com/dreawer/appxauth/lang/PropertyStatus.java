package com.dreawer.appxauth.lang;

public enum PropertyStatus {

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
    private static PropertyStatus get(String name) {
        for (PropertyStatus status : PropertyStatus.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
