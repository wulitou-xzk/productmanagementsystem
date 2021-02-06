package com.productmanage.service;

import com.productmanage.bean.User;

import java.util.List;

public interface UserService {
    String insertUser(User user);

    int deleteUserById(String uId);

    User updateUserByAdmin(User user);

    User editPwdByUser(User user, String uId, String detailId);

    User editPhoneByUser(User user);

    String selectNewUid(Integer userLevel);

    String selectPwd(User user);

    List<User> selectUserIdName(String type);

    User selectUserById(String uId);

    List<User> selectUserByPage(String userName, String sortField, String sortOrder, Integer userLevel, Integer pageIndex, Integer pageSize);

    List<User> selectUserBySheet();

    int findLength(String userName, Integer userLevel);
}
