package com.dreawer.appxauth.form;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <CODE>UserCaseCountForm</CODE>
 *
 * @author fenrir
 * @Date 18-8-10
 */
public class UserCaseCountForm {

    @NotEmpty(message = "EntryError.EMPTY")
    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
