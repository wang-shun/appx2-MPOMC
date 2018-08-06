package com.dreawer.appxauth.model;

import lombok.Data;

import java.util.List;

/**
 * <CODE>Authorization_info</CODE>
 *
 * @author fenrir
 * @Date 18-3-14
 */

@Data
public class Authorization_info {

    String authorizer_appid;

    String authorizer_access_token;

    String expires_in;

    String authorizer_refresh_token;

    List<func_info> func_info;

}
