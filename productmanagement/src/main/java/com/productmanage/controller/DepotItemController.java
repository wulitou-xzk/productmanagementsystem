package com.productmanage.controller;

import com.alibaba.fastjson.JSONArray;
import com.productmanage.bean.DepotItem;
import com.productmanage.service.ProductService;
import com.productmanage.service.UserService;
import com.productmanage.serviceimpl.DepotItemServiceImpl;
import com.productmanage.util.ExeclUtil;
import com.productmanage.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员可执行所有操作；补给员仅可执行补给（新增、修改）
 * 新增、删除、修改、查询补给记录
 * 页面中显示depotid、pdid对应的username、pdname
 */
@RequestMapping("/depotitem")
@RestController
public class DepotItemController {

    @Autowired
    private DepotItemServiceImpl depotItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    // 添加一条补货记录
    @RequestMapping("/insertDepotItem")
    public Object insertDepotItem(String itemJSON){
        String json = itemJSON.replace("[","").replace("]","");
        DepotItem item = (DepotItem)JSONArray.parseObject(json,DepotItem.class);
        String result = depotItemService.insertDepotItem(item);
        return result;
    }

    // 可一次删除单个 / 多个订单信息
    @RequestMapping("/deleteDepotItemById")
    public Object deleteDepotItemById(String id){
        String[] Ids = id.split(",");
        StringBuffer delFail = new StringBuffer("");
        StringBuffer delSuccess = new StringBuffer("");
        for (String itemId : Ids ) {
            int isDel = depotItemService.deleteDepotItemById(itemId);
            if(isDel > 0){
                delSuccess.append(itemId + ",");
            }
            if(isDel <= 0){
                delFail.append(itemId + ",");
            }
        }
        return StringUtil.getResultString(delFail, delSuccess);
    }

    // 更新补货时间
    @RequestMapping("/editDepotItem")
    public Object updateDepotItem(String itemJSON){
        String json = itemJSON.replace("[","").replace("]","");
        DepotItem item = (DepotItem)JSONArray.parseObject(json,DepotItem.class);
        if(depotItemService.updateDepotItem(item) == null){
            return "修改失败";
        }
        return "修改成功";
    }

    // 根据补货编号查询补货记录
    @RequestMapping("/selectDepotItemById")
    public Object selectDepotItemById(String itemId){
        DepotItem item = depotItemService.selectDepotItemById(itemId);
        Map<String, Object> map = getMap(item);
        return map;
    }

    // 查询多条补货记录，可根据补给商品、补给员模糊查询
    @RequestMapping("/selectDepotItemByPage")
    public Object selectDepotItemByPage(String pdId, String depotId, String sortField, String sortOrder, String date,
                                        Integer pageIndex, Integer pageSize){
        List<DepotItem> depotItemList = depotItemService.selectDepotItemByPage(pdId, depotId, sortField, sortOrder, date, pageIndex, pageSize);
        List<Map<String, Object>> newList = new ArrayList<>();
        int total = depotItemService.findLength(pdId, depotId, date);
        for(DepotItem item : depotItemList){
            Map<String, Object> map = getMap(item);
            newList.add(map);
        }
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("data", newList);
        newMap.put("total", total);
        return newMap;
    }

    @RequestMapping("/selectDepotItemBySheet")
    public void selectDepotItemBySheet(String depotId, HttpServletResponse response) throws IOException {
        int i = 1;
        String fileName = "depotItem";
        String[] header = {"补给编号", "补给员编号", "商品编号", "补给添加时间", "补给完成时间", "补给数量"};
        HSSFSheet sheet = ExeclUtil.createSheet("补给记录", header);
        List<DepotItem> depotItemList = depotItemService.selectDepotItemBySheet(depotId);
        for(DepotItem item : depotItemList){
            HSSFRow row = sheet.createRow(i++);
            row.createCell(0).setCellValue(item.getItemId());
            row.createCell(1).setCellValue(item.getDepotId());
            row.createCell(2).setCellValue(item.getPdId());
            row.createCell(3).setCellValue(StringUtil.formatDate(item.getAddDate(),0));
            row.createCell(4).setCellValue(StringUtil.formatDate(item.getDepotDate(),0));
            row.createCell(5).setCellValue(item.getDepotRemain());
            ExeclUtil.setDefaultAutoSizeColumn(sheet, row, 0, 1, 2, 3, 4, 5);
        }
        ExeclUtil.endStep(response, fileName);
    }

    public Map<String, Object> getMap(DepotItem item){
        Map<String, Object> map = new HashMap<>();
        String depotName = userService.selectUserById(item.getDepotId()).getUserName();
        String pdName = productService.selectProductById(item.getPdId()).getPdName();
        map.put("itemId",item.getItemId());
        map.put("pdId",item.getPdId());
        map.put("depotId",item.getDepotId());
        map.put("addDate",item.getAddDate());
        map.put("depotDate",item.getDepotDate());
        map.put("depotRemain",item.getDepotRemain());
        map.put("depotName",depotName);
        map.put("pdName",pdName);
        return map;
    }

    // 获取新的补货编号
    @RequestMapping("/selectNewItemId")
    public Object selectNewItemId(){
        String newItemId = depotItemService.selectNewItemId();
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", newItemId);
        return map;
    }
}
