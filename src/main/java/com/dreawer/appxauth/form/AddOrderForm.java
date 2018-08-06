package com.dreawer.appxauth.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <CODE>AddDecorationServiceForm</CODE> 添加服务表单。
 *
 * @author kael
 * @version 1.0
 * @since Dreawer 2.0
 */

@Data
public class AddOrderForm {

    @NotEmpty(message = "EntryError.EMPTY")
    private List<OrderDetailForm> orderDetails = null; // 商品

    @NotEmpty(message = "EntryError.EMPTY")
    @Length(min = 32, max = 32, message = "EntryError.OVERRANGE")
    private String storeId = null; // 店铺id

    @NotEmpty(message = "EntryError.EMPTY")
    @Length(min = 1, max = 20, message = "EntryError.OVERRANGE")
    private String orderType = null; // 订单类型

    @Length(min = 1, max = 200, message = "EntryError.OVERRANGE")
    private String remark = null; // 备注

    @Length(min = 2, max = 20, message = "EntryError.OVERRANGE")
    private String consigneeName = null; // 收货人

    @Pattern(regexp = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$",
            message = "EntryError.FORMAT")
    private String consigneePhone = null; // 收货人手机号

    @Length(min = 2, max = 200, message = "EntryError.OVERRANGE")
    private String consigneeAddress = null; // 收货人地址

    private String address = null; // 地址

    private String logistics = null; // 物流

    private String promotionId = null; // 优惠活动

    private String coupon = null; // 优惠码

    private String userId = null;

    private String logisticsFee = null; // 物流金额

    private String merchandiseFee = null; // 商品金额

    private String couponFee = null; // 优惠券金额

    private String promotionFee = null; // 活动金额

    private String totalFee = null; // 总金额 

}
