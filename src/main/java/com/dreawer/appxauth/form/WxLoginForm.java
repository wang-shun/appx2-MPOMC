package com.dreawer.appxauth.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <CODE>WxLoginForm</CODE>
 *
 * @author fenrir
 * @Date 18-7-19
 */
@Data
public class WxLoginForm {

    @NotEmpty(message = "EntryError.EMPTY")
    private String encryptedData = null; // 加密信息

    @NotEmpty(message = "EntryError.EMPTY")
    private String iv = null; // 加密算法的初始向量

    @NotEmpty(message = "EntryError.EMPTY")
    private String code = null; // 授权码

    @NotEmpty(message = "EntryError.EMPTY")
    private String appid = null; // 用户小程序appid

    @NotEmpty(message = "EntryError.EMPTY")
    private String oid = null; // 小程序应用id


}
