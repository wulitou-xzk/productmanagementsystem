package com.productmanage.controller;

import com.alibaba.fastjson.JSONArray;
import com.productmanage.bean.TurnOver;
import com.productmanage.bean.User;
import com.productmanage.service.ProductService;
import com.productmanage.service.UserService;
import com.productmanage.serviceimpl.TurnOverServiceImpl;
import com.productmanage.serviceimpl.UserServiceImpl;
import com.productmanage.util.ExeclUtil;
import com.productmanage.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 管理员可执行增删改查；清点员仅可新增、修改（仅限售出数量）
 * 新增、删除、修改、删除商品销售信息
 * 页面中显示显示商品编号对应的商品名称
 */
@RequestMapping("/turnOver")
@RestController
public class TurnOverController {

    @Autowired
    private TurnOverServiceImpl turnOverService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserServiceImpl userService;

    // 添加一条销售信息
    @RequestMapping("/insertTurnOver")
    public Object insertTurnOver(String turnOverJSON){
        String json = turnOverJSON.replace("[","").replace("]","");
        TurnOver turnOver = (TurnOver) JSONArray.parseObject(json, TurnOver.class);
        String result = turnOverService.insertTurnOver(turnOver);
        return result;
    }

    // 一次可删除多条销售信息
    @RequestMapping("/delTurnOverById")
    public Object deleteTurnOverById(String id){
        String[] ids = id.split(",");
        StringBuffer delSuccess = new StringBuffer();
        StringBuffer delFail = new StringBuffer();
        for (String turnId : ids) {
            int isDel = turnOverService.deleteTurnOverById(turnId);
            if(isDel > 0){
                delSuccess.append(turnId + ",");
            }
            if(isDel <= 0){
                delFail.append(turnId + ",");
            }
        }
        return StringUtil.getResultString(delFail, delSuccess);
    }

    // 获取收益情况
    @RequestMapping("/selSaleMoney")
    public Object selectSaleMoney(String pdId, String uId, String saleDate){
        double money = turnOverService.selectSaleMoney(pdId, uId, saleDate);
        return money;
    }

    // 跟据销售编号查询单条销售信息
    @RequestMapping("/selTurnOverById")
    public Object selectTurnOverById(String turnId){
        TurnOver turnOver = turnOverService.selectTurnOverById(turnId);
        Map<String, Object> map = getMap(turnOver);
        return map;
    }

    // 获取多条销售信息。可根据销售日期、商品s编号模糊查询
    @RequestMapping("/selTurnOverByPage")
    public Object selectTurnOverByPage(String pdId, String uId, String saleDate, String sortField,
                                       String sortOrder, Integer pageIndex, Integer pageSize){
        List<TurnOver> turnOverList = turnOverService.selectTurnOverByPage(pdId, uId, saleDate, sortField, sortOrder
        , pageIndex, pageSize);
        List<Map<String, Object>> newList = new ArrayList<>();
        int total = turnOverService.findLength(pdId, uId, saleDate);
        for(TurnOver turnOver : turnOverList){
            Map<String, Object> map = getMap(turnOver);
            newList.add(map);
        }
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("data", newList);
        newMap.put("total", total);
        return newMap;
    }

    @RequestMapping("/selectTurnOverBySheet")
    public void selectTurnOverBySheet(HttpServletResponse response) throws IOException{
        int i = 1;
        String fileName = "inventory";
        String[] header = {"清点编号","清点员编号","商品编号","清点日期","清点数量","金额/元"};
        HSSFSheet sheet = ExeclUtil.createSheet("清点记录", header);
        List<TurnOver> turnOverList = turnOverService.selectTurnOverBySheet();
        for(TurnOver turnOver : turnOverList){
            HSSFRow row = sheet.createRow(i++);
            row.createCell(0).setCellValue(turnOver.getTurnId());
            row.createCell(1).setCellValue(turnOver.getUId());
            row.createCell(2).setCellValue(turnOver.getPdId());
            row.createCell(3).setCellValue(StringUtil.formatDate(turnOver.getSaleDate(), 0));
            row.createCell(4).setCellValue(turnOver.getSaleRemain());
            row.createCell(5).setCellValue(turnOver.getSaleMoney());
            ExeclUtil.setDefaultAutoSizeColumn(sheet, row, 0, 1, 2, 3, 4, 5);
        }
        ExeclUtil.endStep(response, fileName);
    }

    private Map<String, Object> getMap(TurnOver turnOver){
        Map<String, Object> map = new HashMap<>();
        String pdName = productService.selectProductById(turnOver.getPdId()).getPdName();
        User user = userService.selectUserById(turnOver.getUId());
        String userName = (user == null) ? turnOver.getUId() : user.getUserName();
        map.put("turnId",turnOver.getTurnId());
        map.put("uId", turnOver.getUId());
        map.put("userName", userName);
        map.put("pdId",turnOver.getPdId());
        map.put("pdName",pdName);
        map.put("saleDate",new SimpleDateFormat("yyyy-MM-dd").format(turnOver.getSaleDate()));
        map.put("saleRemain",turnOver.getSaleRemain());
        map.put("saleMoney",turnOver.getSaleMoney());
        return map;
    }

    // 获取最新的销售编号
    @RequestMapping("/selNewTurnId")
    public Object selectNewTurnId(){
        String newTurnId = turnOverService.selectNewTurnID();
        Map<String, Object> map = new HashMap<>();
        map.put("turnId", newTurnId);
        return map;
    }
}
