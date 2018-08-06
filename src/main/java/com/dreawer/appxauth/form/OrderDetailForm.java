package com.dreawer.appxauth.form;

import com.dreawer.domain.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailForm extends BaseDomain {

    private static final long serialVersionUID = 5056864307369922564L;

    private String spuId = null; // 商品id

    private String skuId = null; // 商品skuid

    private String name = null; // 商品名称

    private String description = null; // 商品sku描述

    private BigDecimal price = null; // 商品sku金额

    private String photo = null; // 商品sku图片

    private Integer quantity = null; // 商品购买数量


}
