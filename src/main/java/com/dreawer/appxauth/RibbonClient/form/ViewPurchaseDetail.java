package com.dreawer.appxauth.RibbonClient.form;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 购买信息详情视图类。
 */
@Data
@Builder
public class ViewPurchaseDetail {

    private String spuId = null; // 商品ID

    private String skuId = null; // 店铺ID

    private String name = null; // 名称

    private String description = null; // 描述

    private BigDecimal price = null; // 售价

    private String photo = null; // 照片

    private Integer quantity = null; // 数量

    private String promotionId = null; //活动Id


}
