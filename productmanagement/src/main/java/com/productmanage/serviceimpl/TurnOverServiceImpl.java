package com.productmanage.serviceimpl;

import com.productmanage.bean.Product;
import com.productmanage.bean.TurnOver;
import com.productmanage.mapper.ProductMapper;
import com.productmanage.mapper.TurnOverMapper;
import com.productmanage.service.TurnOverService;
import com.productmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TurnOverServiceImpl implements TurnOverService {

    @Autowired
    private TurnOverMapper turnOverMapper;
    @Autowired
    private ProductMapper productMapper;

    // 添加一条商品销售信息
    @Override
    public String insertTurnOver(TurnOver turnOver) {
        Product product = productMapper.selectProductById(turnOver.getPdId());
        double saleMoney = turnOver.getSaleRemain() * product.getPrice();
        turnOver.setSaleMoney(saleMoney);
        int remain = product.getRemain() - turnOver.getSaleRemain();
        if(remain < 0){
            return "填写数量大于库存，请重新输入";
        }else {
            product.setRemain(remain);
            productMapper.updateProduct(product);
            if (turnOverMapper.insertTurnOver(turnOver) > 0) {
                return "销售信息添加成功";
            } else {
                return "销售信息添加失败";
            }
        }
    }

    // 根据销售编号删除一条销售信息
    @Override
    @CacheEvict(value = "turnOver", key = "#turnId")
    public int deleteTurnOverById(String turnId) {
        return turnOverMapper.deleteTurnOverById(turnId);
    }

    @Override
    public int deleteTurnOverByPdId(String pdId) {
        return turnOverMapper.deleteTurnOverByPdId(pdId);
    }

    @Override
    public int updateUidByDelUser(String uId) {
        return turnOverMapper.updateUidByDelUser(uId);
    }

    @Override
    public String selectNewTurnID() {
        StringBuffer newTurnId = new StringBuffer(turnOverMapper.selectNewTurnID());
        newTurnId.append(StringUtil.getIdStr(0));
        return newTurnId.toString();
    }

    @Override
    @Cacheable(cacheNames = {"turnOver"}, key = "#turnId", unless = "#result == null")
    public TurnOver selectTurnOverById(String turnId) {
        TurnOver turnOver = turnOverMapper.selectTurnOverById(turnId);
        return turnOver;
    }

    @Override
    public List<TurnOver> selectTurnOverByPage(String pdId, String uId, String saleDate, String sortField, String sortOrder, Integer pageIndex, Integer pageSize) {
        saleDate = (saleDate == null) ? "" : saleDate.trim();
        uId = (uId == null) ? "" : uId.trim();
        pageIndex *= pageSize;
        List<TurnOver> turnOverList = turnOverMapper.selectTurnOverByPage(pdId, uId, saleDate, sortField, sortOrder, pageIndex, pageSize);
        return turnOverList;
    }

    @Override
    public List<TurnOver> selectTurnOverBySheet() {
        return turnOverMapper.selectTurnOverBySheet();
    }

    @Override
    public int findLength(String pdId, String uId, String saleDate) {
        saleDate = (saleDate == null) ? "" : saleDate.trim();
        uId = (uId == null) ? "" : uId.trim();
        int total = turnOverMapper.findLength(pdId, uId, saleDate);
        return total;
    }

    @Override
    public double selectSaleMoney(String pdId, String uId, String saleDate) {
        saleDate = (saleDate == null) ? "" : saleDate.trim();
        uId = (uId == null) ? "" : uId.trim();
        double money = turnOverMapper.selectSaleMoney(pdId, uId, saleDate);
        return money;
    }
}
