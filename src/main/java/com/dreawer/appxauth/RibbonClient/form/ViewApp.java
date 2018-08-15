package com.dreawer.appxauth.RibbonClient.form;

/**
 * 小程序应用信息视图对象
 */
public class ViewApp {

    private String templetId = null; // 模板ID

    private String appCode = null; // 小程序码

    private String image = null; // 首页配图

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}