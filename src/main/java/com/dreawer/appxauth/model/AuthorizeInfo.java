package com.dreawer.appxauth.model;

import lombok.Data;

/**
 * <CODE>AuthorizeInfo</CODE>
 * 封装授权人信息实体类
 *
 * @author fenrir
 * @Date 17-12-5
 */

@Data
public class AuthorizeInfo {

    Authorizer_info authorizer_info;

    Authorization_info authorization_info;


}



