package com.dreawer.appxauth.lang;

import java.io.Serializable;

/**
 * <CODE>CommentStatus</CODE> 评价状态。
 *
 * @author kael
 * @version 1.0
 * @since Order 1.0
 */
public enum CouponType implements Serializable {

    /**
     * 企业名片
     */
    BSC,

    /**
     * 其他
     */
    OTHER;

    /**
     * 获得优惠码类型。
     *
     * @param name 类型名称。
     * @return 枚举对象。
     * @author kael
     * @since 1.0
     */
    public static CouponType get(String name) {
        for (CouponType type : CouponType.values()) {
            if (type.toString().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
