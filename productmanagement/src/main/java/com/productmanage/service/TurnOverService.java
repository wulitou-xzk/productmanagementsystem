package com.productmanage.service;

import com.productmanage.bean.TurnOver;

import java.util.Date;
import java.util.List;

public interface TurnOverService {
    String insertTurnOver(TurnOver turnOver);

    int deleteTurnOverById(String turnId);

    int deleteTurnOverByPdId(String pdId);

    int updateUidByDelUser(String uId);

    String selectNewTurnID();

    TurnOver selectTurnOverById(String turnId);

    List<TurnOver> selectTurnOverByPage(String pdId, String uId, String saleDate, String sortField,
                                        String sortOrder, Integer pageIndex, Integer pageSize);

    List<TurnOver> selectTurnOverBySheet();

    int findLength(String pdId, String uId, String saleDate);

    double selectSaleMoney(String pdId, String uId, String saleDate);
}
