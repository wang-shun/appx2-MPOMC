package com.dreawer.appxauth.form;


import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.QueryType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * <CODE>CaseQueryForm</CODE>
 * 请求方案查询表单
 * @author fenrir
 * @Date 18-3-1
 */
@ApiModel
@Data
public class CaseQueryForm {

    @ApiModelProperty(value = "方案名称")
    private String name = null; //方案名称

    @ApiModelProperty(value = "客户名称")
    private String customerName = null; //客户名称

    @ApiModelProperty(value = "小程序名称")
    private String appName = null;  //小程序名称

    @ApiModelProperty(value = "联系方式")
    private String contact = null; //联系方式

    @ApiModelProperty(value = "小程序appid")
    private String appid = null; //小程序appid

    @ApiModelProperty(value = "店铺Id")
    @NotEmpty(message = "店铺Id不能为空")
    private String storeId = null; //店铺Id

    @ApiModelProperty(value = "起始时间")
    private Long startTime = null; //起始时间

    @ApiModelProperty(value = "结束时间")
    private Long endTime = null; //结束时间

    @ApiModelProperty(value = "类目id")
    private String categoryId = null; //类目id

    @ApiModelProperty(value = "查询类型 CUSTOMER客户查询,BACKEND管理后台")
    @NotNull(message = "查询类型不能为空")
    private QueryType type = null; //查询类型 CUSTOMER客户查询,BACKEND管理后台

    @ApiModelProperty(value = "页码")
    private Integer pageNo = null;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize = null;

    @ApiModelProperty(value = "发布状态")
    private PublishStatus publishStatus = null; //发布状态

    @ApiModelProperty(value = "userId")
    private String userId = null;



}
