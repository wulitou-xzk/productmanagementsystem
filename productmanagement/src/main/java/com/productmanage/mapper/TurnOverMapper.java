package com.productmanage.mapper;

import com.productmanage.bean.TurnOver;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

// 销售信息
public interface TurnOverMapper {

    int insertTurnOver(TurnOver turnOver);

    int deleteTurnOverById(String turnId);

    int deleteTurnOverByPdId(String pdId);

    int updateUidByDelUser(String uId);

    String selectNewTurnID();

    TurnOver selectTurnOverById(String turnId);

    List<TurnOver> selectTurnOverByPage(@Param("pdId")String pdId, @Param("uId")String uId, @Param("saleDate")String saleDate, @Param("sortField")String sortField,
    		@Param("sortOrder")String sortOrder, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize);

    List<TurnOver> selectTurnOverBySheet();

    int findLength(@Param("pdId")String pdId, @Param("uId")String uId, @Param("saleDate")String saleDate);

    double selectSaleMoney(@Param("pdId")String pdId, @Param("uId")String uId, @Param("saleDate")String saleDate);
}
