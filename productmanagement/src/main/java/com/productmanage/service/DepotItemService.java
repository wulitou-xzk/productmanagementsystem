package com.productmanage.service;

import com.productmanage.bean.DepotItem;

import java.util.List;

public interface DepotItemService {
    String insertDepotItem(DepotItem item);

    int deleteDepotItemById(String itemIds);

    int deleteDepotItemByPdId(String pdId);

    int deleteDepotIdByDepotId(String depotId);

    DepotItem updateDepotItem(DepotItem item);

    String selectNewItemId();

    DepotItem selectDepotItemById(String itemId);

    List<DepotItem> selectDepotItemByPage(String pdId, String depotId, String sortField,
                                          String sortOrder,  String date, Integer pageIndex, Integer pageSize);

    List<DepotItem> selectDepotItemBySheet(String depotId);

    int findLength(String pdName, String depotId, String date);
}
