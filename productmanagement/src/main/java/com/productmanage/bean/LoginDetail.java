package com.productmanage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// login detail message - 登录记录
@Data
public class LoginDetail implements Serializable {
    private String detailId;        // 记录编号
    private String uId;             // 登录的用户的编号
    private Date currentLogin;      // 登录时间
    private Date exitLogin;         // 退出登录时间

    public LoginDetail() {
    }

    public LoginDetail(String uId) {
        this.uId = uId;
    }

    public LoginDetail(String detailId, String uId, Date currentLogin) {
        this.detailId = detailId;
        this.uId = uId;
        this.currentLogin = currentLogin;
    }
}
