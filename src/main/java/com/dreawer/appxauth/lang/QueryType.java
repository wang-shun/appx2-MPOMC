package com.dreawer.appxauth.lang;

public enum QueryType {

    /**
     * 客户
     **/
    CUSTOMER,

    /**
     * 后台
     **/
    BACKEND;

    /**
     * 获取类目状态
     *
     * @param name
     * @return 枚举对象
     */
    public static QueryType get(String name) {
        for (QueryType type : QueryType.values()) {
            if (type.toString().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
