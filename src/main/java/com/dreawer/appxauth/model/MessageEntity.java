package com.dreawer.appxauth.model;

import lombok.Data;

/**
 * <CODE>MessageEntity</CODE>
 *
 * @author fenrir
 * @Date 17-12-12
 */

@Data
public class MessageEntity {

    private String notifierIdentityId;

    private String notifiedId;

    private String notifiedIdentityId;

    private String type;

    private String title;

    private String content;

    private String objectId;

    private String objectCategory;

    private String remark;


}
