package com.dreawer.appxauth.domain;

import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.SaleMode;
import com.dreawer.domain.BaseDomain;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <CODE>UserCase</CODE>
 * 已购买小程序实体类
 *
 * @author fenrir
 * @Date 17-11-6
 */

@Data
public class UserCase extends BaseDomain {

    private static final long serialVersionUID = 987547913809928583L;

    private String name = null; //方案名称

    private String storeId = null; //店铺Id

    private String logo = null; //logo

    private String backendUrl = null; //后台Url

    private String orderIds = null; //订单ID列表

    private String appName = null; //小程序名称

    private String themeName = null; //主题名称

    private String previewQRcode = null; //预览Url

    private String releaseQRcode = null; //发布Url

    private String categoryId = null; //小程序主题Id

    private String appId = null; //小程序appId

    private String domain = null; //小程序域名

    private String category = null; //业务别名

    private SaleMode saleMode = null; //销售状态 ADVANCE-预售,DEFALUT 默认

    private String clientName = null; //客户名称

    private String clientContact = null; //客户联系方式

    private String delegatePhone = null; //委托发布手机号

    private PublishStatus publishStatus = null; //发布状态

    private String durationType = null; //付费周期

    private Timestamp expireDate = null; //到期时间

    private String createrId = null; // 创建者

    private Timestamp createTime = null; // 创建时间

    private String updaterId = null; // 更新者

    private Timestamp updateTime = null; // 更新时间

    private String auditResult = null; //小程序审核失败原因


}
