package com.productmanage.mapper;


import com.productmanage.bean.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int insertUser(User user);

    int deleteUserById(String uId);

    int updateUserByAdmin(User user);

    int editPwdByUser(@Param("uId")String uId, @Param("phone")String phone, @Param("userName")String userName, @Param("newPwd")String newPwd);

    String selectNewUid(@Param("userType")String userType, @Param("userLevel")Integer userLevel);

    String findPwd(User user);

    User selectUserById(String uId);

    List<User> selectUserIdName(String type);

    List<User> selectUserBySheet();

    List<User> selectUserByPage(@Param("userName")String userName, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, 
    		@Param("userLevel")Integer userLevel, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize);

    int findLength(@Param("userName")String userName, @Param("userLevel")Integer userLevel);

    int updateUserPhoneByUser(@Param("uId")String uId, @Param("userName")String userName, 
    		@Param("oldPhone")String oldPhone, @Param("phone")String phone);
}
