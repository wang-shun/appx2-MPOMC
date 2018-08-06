package com.dreawer.appxauth.lang;

import java.io.Serializable;

/**
 * <CODE>CommentStatus</CODE> 评价状态。
 *
 * @author kael
 * @version 1.0
 * @since Order 1.0
 */
public enum CouponStatus implements Serializable {

    /**
     * 未使用
     */
    UNUSED,

    /**
     * 已使用
     */
    USED;

    /**
     * 获得优惠码状态。
     *
     * @param name 状态名称。
     * @return 枚举对象。
     * @author kael
     * @since 1.0
     */
    public static CouponStatus get(String name) {
        for (CouponStatus status : CouponStatus.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
