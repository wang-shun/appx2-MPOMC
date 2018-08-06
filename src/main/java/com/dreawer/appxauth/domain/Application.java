package com.dreawer.appxauth.domain;

import com.dreawer.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

/**
 * <CODE>Application</CODE>
 *
 * @author fenrir
 * @Date 18-7-13
 */

@Data
public class Application extends BaseDomain {

    private String appid = null; //小程序appid

    private String organizationId = null; //小程序应用AppID

    private String openid = null;

    private String sessionKey = null;

    private Date createTime = null;

}
