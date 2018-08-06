package com.dreawer.appxauth.form;


import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.QueryType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * <CODE>CaseQueryForm</CODE>
 * 请求方案查询表单
 * @author fenrir
 * @Date 18-3-1
 */
public class CaseQueryForm {

    private String name = null; //方案名称

    private String customerName = null; //客户名称

    private String appName = null;  //小程序名称

    private String contact = null; //联系方式

    private String appid = null; //小程序appid

    @NotEmpty(message = "店铺Id不能为空")
    private String storeId = null; //店铺Id

    private Long startTime = null; //起始时间

    private Long endTime = null; //结束时间

    private String themeId = null; //主题id

    @NotNull(message = "查询类型不能为空")
    private QueryType type = null; //查询类型 CUSTOMER客户查询,BACKEND管理后台

    private Integer pageNo = null;
    private Integer pageSize = null;

    private PublishStatus publishStatus = null; //发布状态

    private String userId = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public QueryType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = QueryType.get(type);
    }

    public PublishStatus getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = PublishStatus.get(publishStatus);
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
