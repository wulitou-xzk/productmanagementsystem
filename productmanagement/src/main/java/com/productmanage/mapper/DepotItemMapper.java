package com.productmanage.mapper;

import com.productmanage.bean.DepotItem;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DepotItemMapper {
    int insertDepotItem(DepotItem item);

    int deleteDepotItemById(String itemId);

    int deleteDepotItemByPdId(String pdId);

    int deleteDepotIdByDepotId(String depotId);

    int updateDepotItem(DepotItem item);

    String selectNewItemId();

    DepotItem selectDepotItemById(String itemId);

    List<DepotItem> selectDepotItemByPage(@Param("pdId")String pdId, @Param("depotId")String depotId, @Param("sortField")String sortField,
    		@Param("sortOrder")String sortOrder,  @Param("date")String date, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize);

    List<DepotItem> selectDepotItemBySheet(String depotId);

    int findLength(@Param("pdId")String pdId, @Param("depotId")String depotId, @Param("date")String date);
}
