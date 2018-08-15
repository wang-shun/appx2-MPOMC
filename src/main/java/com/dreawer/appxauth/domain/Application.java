package com.dreawer.appxauth.domain;

import com.dreawer.domain.BaseDomain;
import lombok.Data;

/**
 * <CODE>Application</CODE>
 * 小程序应用实体类
 * @author fenrir
 * @Date 18-8-14
 */
@Data
public class Application extends BaseDomain {

    private String appId = null;

    private String organizationId = null;

}
