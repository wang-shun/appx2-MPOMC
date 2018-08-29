package com.dreawer.appxauth.utils;

import java.sql.Timestamp;

/**
 * <CODE>BaseUtils</CODE>
 * 基础工具类
 *
 * @author fenrir
 * @Date 18-8-29
 */
public class BaseUtils {

    /**
     * 获取当前系统时间。
     *
     * @return 当前系统时间。
     * @author David Dai
     * @since 2.0
     */
    public static Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }
}
