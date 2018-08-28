package com.dreawer.appxauth.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <CODE>CreateUserCaseForm</CODE>
 *
 * @author fenrir
 * @Date 18-7-23
 */

@Data
public class CreateUserCaseForm {

    @NotEmpty(message = "EntryError.EMPTY")
    private String userId = null; //用户ID

    @NotEmpty(message = "EntryError.EMPTY")
    private String orderId = null; //订单ID

    @NotEmpty(message = "EntryError.EMPTY")
    private String skuId = null; //商品SkuID

    @NotEmpty(message = "EntryError.EMPTY")
    private String spuId = null; //商品ID

    @NotEmpty(message = "EntryError.EMPTY")
    private String type = null;

    private String caseId = null;

}
