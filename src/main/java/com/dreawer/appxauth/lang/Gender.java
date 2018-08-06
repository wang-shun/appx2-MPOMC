package com.dreawer.appxauth.lang;

public enum Gender {

    /**
     * 男
     **/
    MALE,

    /**
     * 女
     **/
    FEMALE;

    /**
     * 获取性别
     *
     * @param name
     * @return 枚举对象
     */
    public static Gender get(String name) {
        for (Gender gender : Gender.values()) {
            if (gender.toString().equalsIgnoreCase(name)) {
                return gender;
            }
        }
        return null;
    }

}
