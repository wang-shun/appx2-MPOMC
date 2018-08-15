package com.dreawer.appxauth.RibbonClient.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * SKU视图类。
 */

/**
 * @author lenovo
 */
@Data
public class ViewSku {

    private String id = null; // SKUID

    private String inventoryType = null; // 库存类型

    private Integer inventory = null; // 库存

    private Integer lockedInventory = null; // 锁定库存数

    private Integer purchasableInventory = null; // 可下单库存数

    private Integer salesVolume = null; //起售量

    private BigDecimal originalPrice = null; // 原价

    private BigDecimal price = null; // 售价

    private String description = null; // 描述

    private String code = null; // 编码

    private String barcode = null; // 条码

    private String remark = null; // 备注


}
