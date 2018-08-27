package com.dreawer.appxauth.RibbonClient.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品视图类。
 */

@Data
public class ViewGoods {

    private String id = null; // 商品ID

    private String storeId = null; // 店铺ID

    private String name = null; // 名称

    private String categoryId = null; // 类目ID（子类目）

    private BigDecimal minPrice = null; // 最低售价

    private BigDecimal minOriginalPrice = null; // 最低原价

    private String inventoryType = null; // 库存类型

    private Integer totalInventory = null; // 总库存

    private Integer totalLockedInventory = null; // 锁定总库存

    private Integer totalPurchasableInventory = null; // 可下单总库存

    private String mainDiagram = null; // 主图

    private String detail = null; // 详情

    private String service = null; // 售后服务

    private String status = null; // 状态

    private Boolean isRecommend = null; //是否推荐

    private String createrId = null; // 创建者ID

    private Long createTime = null; // 创建时间

    private String remark = null; // 备注

    private List<ViewGroup> groups = null; // 分组列表

    private List<ViewSku> skus = null; // SKU列表

    private List<ViewGoodsPropertyName> propertyNames = null; //商品属性名列表

    private ViewApp viewApp = null; // 小程序应用信息视图


}
