package com.dreawer.appxauth.form;

import com.dreawer.appxauth.lang.PublishStatus;

public class UserCaseQueryForm {

    private String skuId = null;

    private PublishStatus publishStatus = null;


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public PublishStatus getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String status) {
        this.publishStatus = PublishStatus.get(status);
    }

}
