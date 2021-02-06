package com.productmanage.mapper;

import com.productmanage.bean.LoginDetail;
import com.productmanage.bean.User;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LoginDetailMapper {
    int insertLoginDetail(LoginDetail detail);

    int deleteLoginDetailById(String detailId);

    int deleteLoginDetailByUid(String uId);

    int updateLoginDetail(@Param("uId")String uId, @Param("detailId")String detailId);

    LoginDetail isLogin(String uId);

    String selectNewDetailId();

    LoginDetail selectLoginDetailById(@Param("detailId")String detailId);

    List<LoginDetail> selctLoginDetailByPage(@Param("uId")String uId, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("date")String date,
    		@Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize);

    List<LoginDetail> selectLoginDetailBySheet();

    int findLength(@Param("uId")String uId, @Param("date")String date);

}
