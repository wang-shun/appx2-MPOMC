package com.dreawer.appxauth.form;

import com.dreawer.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

@Data
public class TokenUser extends BaseDomain {

    private static final long serialVersionUID = 8650970185938005907L;

    private String organizeId = null; // 组织id

    private String password = null; // 登录密码，有效长度：6~20

    private String phoneNumber = null; // 手机号

    private String email = null; // E-Mail（注册邮箱）

    private String petName = null; // 昵称

    private String mugshot = null; // 头像

    private Date createTime = null; // 创建时间


}
