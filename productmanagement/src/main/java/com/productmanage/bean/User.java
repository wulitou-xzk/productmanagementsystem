package com.productmanage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// user message - 用户信息
@Data
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String uId;         // 用户编号
    private String userName;    // 用户名
    private String password;    // 登录密码
    private Integer userLevel;  // 用户等级（类别）
    private String remark;      // 备注信息
    private Date addDate;       // 添加日期
    private String phone;       // 员工电话号码

    public User() {
    }

    public User(String uId, String userName, String password) {
        this.uId = uId;
        this.userName = userName;
        this.password = password;
    }

    public User(String uId, String userName, String password, String remark, Date addDate) {
        this.uId = uId;
        this.remark = remark;
        this.addDate = addDate;
        this.userName = userName;
        this.password = password;
    }

    // 添加用户
    public User(String uId, String userName, String password, Integer userLevel, String remark, Date addDate, String phone) {
        this.uId = uId;
        this.remark = remark;
        this.addDate = addDate;
        this.userName = userName;
        this.password = password;
        this.userLevel = userLevel;
        this.phone = phone;
    }

    // 通过表格添加员工
    public User(String userName, Integer userLevel, String remark, Date addDate, String phone) {
        this.remark = remark;
        this.addDate = addDate;
        this.userName = userName;
        this.userLevel = userLevel;
        this.phone = phone;
    }

    public User(String uId, String userName, Integer userLevel, String remark, Date addDate, String phone) {
        this.uId = uId;
        this.remark = remark;
        this.addDate = addDate;
        this.userName = userName;
        this.userLevel = userLevel;
        this.phone = phone;
    }

    // 员工找回密码
    public User(String uId, String userName, Integer userLevel, String phone) {
        this.uId = uId;
        this.userName = userName;
        this.userLevel = userLevel;
        this.phone = phone;
    }

    public User(String userName, String password, Integer userLevel, String remark, Date addDate) {
        this.remark = remark;
        this.addDate = addDate;
        this.userName = userName;
        this.password = password;
        this.userLevel = userLevel;
    }


}
