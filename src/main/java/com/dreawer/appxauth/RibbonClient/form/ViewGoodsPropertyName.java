package com.dreawer.appxauth.RibbonClient.form;

import lombok.Data;

import java.util.List;

/**
 * 商品属性名视图类。
 */
@Data
public class ViewGoodsPropertyName {

    private String id = null; // 商品属性名ID

    private String propertyNameId = null; // 属性名ID

    private String name = null; // 名称

    private String categoryId = null; // 类目ID（子类目）

    private String type = null; // 属性名类型（SYSTEM-系统、CUSTOMER-客户）

    private Integer squence = null; // 排列序号

    private Boolean isSearch = null; // 是否搜索框

    private Boolean isRadio = null; // 是否单选框

    private Boolean isCheckbox = null; // 是否复选框

    private Boolean isInput = null; // 是否输入框

    private Boolean isSelect = null; // 是否下拉框

    private Boolean isVisualEditor = null; // 是否富文本编辑器

    private Boolean isColor = null; // 是否颜色属性

    private Boolean isRequired = null; // 是否必须属性

    private Boolean isSales = null; // 是否销售属性

    private Boolean isBasic = null; // 是否基础属性

    private Boolean isKey = null; // 是否关键属性

    private Boolean isImage = null; // 是否图片属性

    private List<ViewGoodsPropertyValue> propertyValues = null; // 商品属性值列表


}
