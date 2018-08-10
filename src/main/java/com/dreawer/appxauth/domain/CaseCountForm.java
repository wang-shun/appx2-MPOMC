package com.dreawer.appxauth.domain;

import com.dreawer.appxauth.lang.AlertStatus;

import java.sql.Timestamp;

/**
 * <CODE>CaseCountForm</CODE>
 * 查看用户解决方案数量和到期预警
 *
 * @author fenrir
 * @Date 18-8-10
 */
public class CaseCountForm {

    private String userId;

    private Integer count;

    private Timestamp expireDate = null; //到期时间

    private Boolean yellow;

    private Boolean red;

    public Boolean getYellow() {
        return yellow;
    }

    public void setYellow(Boolean yellow) {
        this.yellow = yellow;
    }

    public Boolean getRed() {
        return red;
    }

    public void setRed(Boolean red) {
        this.red = red;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Timestamp getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Timestamp expireDate) {
        this.expireDate = expireDate;
    }


}
