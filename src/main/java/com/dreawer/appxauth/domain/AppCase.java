package com.dreawer.appxauth.domain;

import com.dreawer.appxauth.lang.Platform;
import com.dreawer.domain.BaseDomain;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <CODE>AppCase</CODE>
 * 小程序方案实体类
 *
 * @author fenrir
 * @Date 17-10-24
 */
@Data
public class AppCase extends BaseDomain {

    private static final long serialVersionUID = -3302712110526567596L;

    private String title = null; //标题

    private String subtitle = null; //二级标题

    private Platform platform = null; //支持平台

    private String backend = null; // 后台管理url

    private String price = null; //价格

    private String domain = null; //小程序域名

    private String industry = null; //适用行业

    private String intro = null; //简介

    private String defaultQRcode = null; //默认小程序预览Url

    private String spuId = null; //解决方案spuId

    private String titleImage = null; //题图

    private String backgroundImage = null; //背景图

    private String createrId = null; // 创建者

    private Timestamp createTime = null; // 创建时间

    private String updaterId = null; // 更新者

    private Timestamp updateTime = null; // 更新时间

}
