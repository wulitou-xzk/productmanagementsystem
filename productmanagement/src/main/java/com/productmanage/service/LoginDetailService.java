package com.productmanage.service;

import com.productmanage.bean.LoginDetail;
import com.productmanage.bean.User;

import java.util.Date;
import java.util.List;

public interface LoginDetailService {
    User insertLoginDetail(LoginDetail detail, User user);

    int deleteLoginDetailById(String detailIds);

    int deleteLoginDetailByUid(String uId);

    String updateLoginDetail(String uId, String detailId);

    LoginDetail isLogin(String uId);

    String selectNewDetailId();

    LoginDetail selectLoginDetailById(String detailId);

    List<LoginDetail> selctLoginDetailByPage(String uId, String sortField, String sortOrder, String date,
                                             Integer pageIndex, Integer pageSize);

    List<LoginDetail> selectLoginDetailBySheet();

    int findLength(String uId, String date);

    String selectExitLoginById(String detailId);

    String quitByAdmin(String detailId);
}
