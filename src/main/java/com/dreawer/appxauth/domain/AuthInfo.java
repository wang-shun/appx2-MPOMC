package com.dreawer.appxauth.domain;

import com.dreawer.appxauth.lang.AppType;
import com.dreawer.domain.BaseDomain;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <CODE>AuthInfo</CODE>
 *
 * @author fenrir
 * @Date 18-7-3
 */

@Data
public class AuthInfo extends BaseDomain {


    private String appid = null;

    private String authorizationCode = null;

    private String accessToken = null;

    private String refreshToken = null;

    private Date expireIn = null;

    private AppType appType = null;

    private String createrId = null;

    private Timestamp createTime = null;

    private Timestamp updateTime = null;
}
