package com.dreawer.appxauth.RibbonClient.form;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 分组视图类。
 */
@Data
public class ViewGroup {

    private String id = null; // 分组ID

    private String storeId = null; // 店铺

    private String name = null; // 名称

    private String parentId = null; // 父分组ID

    private Boolean isParent = null; // 是否父分组

    private Integer squence = null; // 排列序号

    private String logo = null; // LOGO

    private String intro = null; // 简介

    private String status = null; // 状态

    private String url = null; // 跳转地址（APPX专用）

    private Integer goodsQuantity = null; // 商品数量

    private String createrId = null; // 创建者ID

    private Timestamp createTime = null; // 创建时间

    private String remark = null; // 备注


}
