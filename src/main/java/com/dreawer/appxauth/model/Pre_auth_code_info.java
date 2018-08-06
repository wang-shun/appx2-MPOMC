package com.dreawer.appxauth.model;

import lombok.Data;

/**
 * <CODE>Pre_auth_code_info</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */

@Data
public class Pre_auth_code_info {

    private String pre_auth_code;

    private String expires_in;
}
