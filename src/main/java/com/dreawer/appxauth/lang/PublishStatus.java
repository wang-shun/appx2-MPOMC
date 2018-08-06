package com.dreawer.appxauth.lang;

public enum PublishStatus {

    /**
     * 已发布
     **/
    PUBLISHED,

    /**
     * 未发布
     **/
    UNPUBLISHED,

    /**
     * 委托发布中
     **/
    DELEGATEDPUBLISHING,

    /**
     * 审核中
     **/
    PENDING,

    /**
     * 已授权
     **/
    AUTHORIZED,

    /**
     * 未授权
     **/
    UNAUTHORIZED,

    /**
     * 预售
     **/
    ADVANCE,

    /**
     * 已过期
     **/
    EXPIRED,

    /**
     * 审核失败
     **/
    AUDITFAILED,

    /**
     * 提交失败
     **/
    SUBMITFAILED,

    /**
     * 缺少提交条件
     **/
    MISSINGCONDITION,

    /**
     * 未支付
     **/
    UNPAID;


    /**
     * 获取发布状态
     *
     * @param name
     * @return 枚举对象
     */
    public static PublishStatus get(String name) {
        for (PublishStatus status : PublishStatus.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
