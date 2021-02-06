package com.productmanage.serviceimpl;

import com.productmanage.bean.DepotItem;
import com.productmanage.mapper.DepotItemMapper;
import com.productmanage.service.DepotItemService;
import com.productmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotItemServiceImpl implements DepotItemService {

    @Autowired
    private DepotItemMapper itemMapper;

    // 新增一条补给记录
    @Override
    public String insertDepotItem(DepotItem item) {
        if(itemMapper.insertDepotItem(item) > 0){
            return "记录添加成功";
        }
        return "记录添加失败";
    }

    // 删除补给记录，仅管理员可操作
    @Override
    @CacheEvict(value = "item", key = "#itemId")
    public int deleteDepotItemById(String itemId) {
        return itemMapper.deleteDepotItemById(itemId);
    }

    @Override
    public int deleteDepotItemByPdId(String pdId) {
        return itemMapper.deleteDepotItemByPdId(pdId);
    }

    @Override
    public int deleteDepotIdByDepotId(String depotId) {
        return itemMapper.deleteDepotIdByDepotId(depotId);
    }

    // 更新补给信息，仅可更新补给时间、补给数量
    @Override
    @CachePut(value = "item", key = "#item.itemId", unless = "#result == null")
    public DepotItem updateDepotItem(DepotItem item) {
        if(itemMapper.updateDepotItem(item) > 0){
            return item;
        }
        return null;
    }

    // 获取一条新的补给编号
    @Override
    public String selectNewItemId() {
        StringBuffer itemId = new StringBuffer(itemMapper.selectNewItemId());
        itemId.append(StringUtil.getIdStr(2));
        return itemId.toString();
    }

    // 获取单条补给记录
    @Override
    @Cacheable(cacheNames = {"item"}, key = "#itemId", unless = "#result == null")
    public DepotItem selectDepotItemById(String itemId) {
        DepotItem item = itemMapper.selectDepotItemById(itemId);
        return item;
    }

    // 获取多条补给记录
    @Override
    public List<DepotItem> selectDepotItemByPage(String pdId, String depotId, String sortField, String sortOrder,
                                                String date, Integer pageIndex, Integer pageSize) {
        date = (date == null) ? "" : date.trim();
        pageIndex *= pageSize;
        List<DepotItem> itemList = itemMapper.selectDepotItemByPage(pdId, depotId, sortField, sortOrder, date, pageIndex, pageSize);
        return itemList;
    }

    @Override
    public List<DepotItem> selectDepotItemBySheet(String depotId) {
        depotId = (depotId == null) ? "" : depotId.trim();
        return itemMapper.selectDepotItemBySheet(depotId);
    }

    @Override
    public int findLength(String pdId, String depotId, String date) {
        date = (date == null) ? "" : date.trim();
        int total = itemMapper.findLength(pdId, depotId, date);
        return total;
    }
}
