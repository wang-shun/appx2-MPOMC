package com.dreawer.appxauth.RibbonClient.form;

import lombok.Data;

/**
 * 商品属性值视图类。
 */
@Data
public class ViewGoodsPropertyValue {

    private String id = null; // 商品属性值ID

    private String propertyValueId = null; // 属性值ID

    private String propertyNameId = null; // 属性名ID

    private String goodsId = null; // 商品ID

    private String propertyNameType = null; // 属性名类型（SYSTEM-系统、CUSTOMER-客户）

    private String type = null; // 属性值类型（SYSTEM-系统、CUSTOMER-客户）

    private Integer squence = null; // 排列序号

    private String name = null; // 名称

    private Boolean isDefaultImage = null; // 是否默认图片

    private String imageUrl = null; // 图片地址

    private String remark = null; // 备注


}
