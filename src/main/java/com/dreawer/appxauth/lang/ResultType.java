package com.dreawer.appxauth.lang;

public enum ResultType {

    /**
     * 权限缺失
     **/
    PERMISSIONDENIED,

    /**
     * 主体不一致
     **/
    PRINCIPAL,

    /**
     * 类目未填写
     **/
    CATEGORY,

    /**
     * 名称未填写
     **/
    NAME,

    /**
     * 已经授权给其他第三方平台
     */
    ALREADYAUTHORIZED;


    /**
     * 获得是否失败类型。
     *
     * @param name 类型名称。
     * @return 枚举对象。
     * @author kael
     * @since 1.0
     */
    public static ResultType get(String name) {
        for (ResultType type : ResultType.values()) {
            if (type.toString().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
