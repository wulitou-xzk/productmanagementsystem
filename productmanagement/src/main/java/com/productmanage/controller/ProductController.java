package com.productmanage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.productmanage.bean.Product;
import com.productmanage.serviceimpl.DepotItemServiceImpl;
import com.productmanage.serviceimpl.NotificationServiceImpl;
import com.productmanage.serviceimpl.ProductServiceImpl;
import com.productmanage.serviceimpl.TurnOverServiceImpl;
import com.productmanage.util.ExeclUtil;
import com.productmanage.util.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员可执行所有操作；获取补给员尽可新增商品
 * 新增、删除、修改、查询商品信息
 */
@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private DepotItemServiceImpl depotItemService;
    @Autowired
    private TurnOverServiceImpl turnOverService;

    // 新增商品
    @RequestMapping("/insertProduct")
    public Object insertProduct(String productJSON){
        String json = productJSON.replace("[","").replace("]","");
        Product product = JSONArray.parseObject(json,Product.class);
        String result = productService.insertProduct(product);
        return result;
    }

    @RequestMapping("/insertProductBySheet")
    public Object insertProductBySheet(MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();
        String result = batchImport(fileName, file);
        return result;
    }

    private String batchImport(String fileName, MultipartFile file) throws Exception {
        if(!ExeclUtil.isExecl(fileName)){
            return "上传文件格式不对，请使用xls文件";
        }
        StringBuffer insertFail = new StringBuffer("");
        Sheet sheet = ExeclUtil.createSheet(fileName, file);
        if(sheet == null){
            return "文件中没有表格或者您使用了xlsx文件格式，请使用xls文件";
        }
        for(int i = 1; i <= sheet.getLastRowNum(); i++){
            StringBuffer fail = new StringBuffer("第" + (i + 1) + "行商品：");
            Row row = sheet.getRow(i);
            if(row == null){    // 遇到空行时跳过
                continue;
            }
            Object obj = checkDataFromExcel(row, fail);
            if(obj instanceof Product){
                productService.insertProduct((Product) obj);
                continue;
            }
            insertFail.append(fail + "；<br>");
         }
        return insertFail.length() == 0 ? "全部商品添加成功" : insertFail.append("其余商品添加成功").toString();
    }

    // 校验表格中的数据
    private Object checkDataFromExcel(Row row, StringBuffer fail) {
        int cellIndex = 0;
        String pdName = null;       // 商品名称
        String producer = null;     // 商品生产商
        Double price = null;        // 商品价格
        Date produceDate = null;    // 商品生产日期
        Date expirationDate = null; // 商品过期时间
        Double r = null;            // 用于存储从表格获取的值
        Integer remain = null;      // 商品剩余数量
        Date addDate = null;        // 商品添加时间
        String remark = null;       // 备注信息

        try{
            pdName = row.getCell(cellIndex++).getStringCellValue().trim();
            pdName.substring(1);
            producer = row.getCell(cellIndex++).getStringCellValue().trim();
            producer.substring(1,2);
            price = row.getCell(cellIndex++).getNumericCellValue();
            produceDate = row.getCell(cellIndex++).getDateCellValue();
            expirationDate = row.getCell(cellIndex++).getDateCellValue();
            r = row.getCell(cellIndex++).getNumericCellValue();
            remain = r.intValue();
            addDate = row.getCell(cellIndex++).getDateCellValue();
            remark = row.getCell(cellIndex++).getStringCellValue().trim();
        } catch (Exception e){
            return getFailString(cellIndex - 1, fail);
        }
        String pdId = productService.selectNewPdId();
        Product product = new Product(pdId, pdName, price, producer, produceDate, expirationDate, remain, remark, addDate);
        return product;
    }

    // 通过index判断是哪种错误
    private StringBuffer getFailString(int index, StringBuffer fail) {
        // index ==> 0 / 1 / 2 / 3 / 4 / 5 / 6
        switch (index){
            case 0: fail.append("名称未正确填写，"); break;
            case 1: fail.append("生产商未正确填写，"); break;
            case 2: fail.append("价格未正确填写，"); break;
            case 3: fail.append("生产日期未正确填写，"); break;
            case 4: fail.append("过期时间未正确填写，"); break;
            case 5: fail.append("数量未正确填写，"); break;
            case 6: fail.append("添加日期未正确填写，"); break;
        }
        return fail;
    }

    // 删除一个商品时需要级联删除通知信息表、补给订单表、销售信息表中的相关数据
    @RequestMapping("/deleteProductById")
    public Object deleteProductById(String id){
        String[] Ids = id.split(",");
        StringBuffer delFail = new StringBuffer("");
        StringBuffer delSuccess = new StringBuffer("");
        for(String pdId : Ids){
            notificationService.deleteByPdId(pdId);
            depotItemService.deleteDepotItemByPdId(pdId);
            turnOverService.deleteTurnOverByPdId(pdId);
            String pdName = productService.selectProductById(pdId).getPdName();
            int isDel = productService.deleteProductById(pdId);
            if(isDel > 0){
                delSuccess.append(pdName + ",");
            }
            if(isDel <= 0){
                delFail.append(pdName + ",");
            }
        }
        return StringUtil.getResultString(delFail, delSuccess);
    }

    // 更新商品信息（可更新产品价格、产品剩余数量）
    @RequestMapping("/editProduct")
    public Object updateProduct(String productJSON){
        String json = productJSON.replace("[","").replace("]","");
        Product product = JSONArray.parseObject(json,Product.class);
        if(productService.updateProduct(product) == null){
            return "商品更新失败";
        }
        return "商品更新成功";
    }

    // 根据商品编号查询商品信息
    @RequestMapping("/selectProductById")
    public Object selectProductById(String pdId){
        Product product = productService.selectProductById(pdId);
        return product;
    }

    // 获取多条商品信息，可根据商品名称进行模糊查询
    @RequestMapping("/selectProductByPage")
    public Object selectProductByPage(String pdName, String sortField, String sortOrder, Integer pageIndex, Integer pageSize){
        List<Product> productList = productService.selectProductByPage(pdName, sortField, sortOrder, pageIndex, pageSize);
        int total = productService.findLength(pdName);
        Map<String, Object> map = new HashMap<>();
        map.put("data", productList);
        map.put("total", total);
        return map;
    }

    // 获取所有商品的id及名称
    @RequestMapping("/selectPdIdPdName")
    public Object selectPdIdPdName(){
        List<Product> productList = productService.selectProductIdName();
        return productList;
    }

    // 将数据库中的商品信息以表格导出
    @RequestMapping("/selectProductBySheet")
    public void selectProductBySheet(HttpServletResponse response) throws IOException {
        int i = 1;
        String fileName = "productInfo";
        List<Product> productList = productService.selectProductBySheet();
        String[] header = {"商品编号", "商品名称", "生产商", "价格/元","生产日期", "过期时间", "数量", "添加时间", "备注"};
        HSSFSheet sheet = ExeclUtil.createSheet("商品信息", header);
        for (Product product : productList) {
            HSSFRow row = sheet.createRow(i++);
            setRow(row, product, 0);
            ExeclUtil.setDefaultAutoSizeColumn(sheet, row, 0, 1, 2, 3, 4, 5, 6, 7, 8);
        }
        ExeclUtil.endStep(response, fileName);
    }

    @RequestMapping("/getFileDemo")
    public void getFileDemo(HttpServletResponse response) throws IOException {
        String fileName = "productInfoDemo";
        String[] header = {"商品名称", "生产商", "价格/元","生产日期", "过期时间", "数量", "添加时间", "备注"};
        HSSFSheet sheet = ExeclUtil.createSheet("商品信息", header);
        HSSFRow row = sheet.createRow(1);
        Date d = new Date();
        Product product = new Product("可口可乐", 3, "可口可乐生产公司", d, d, 12, "", d);
        product.setRemark("单价：3元/瓶；数量：23箱（说明：本文件中的数据仅用于示范，" +
                "导入系统前需将本行数据删除，并根据格式添加新的数据，否则将无法添加）");
        setRow(row, product, 0);
        ExeclUtil.setDefaultAutoSizeColumn(sheet, row, 0, 1, 2, 3, 4, 5, 6, 7);
        ExeclUtil.endStep(response, fileName);
    }

    private void setRow(HSSFRow row, Product product, int index){
        if(product.getPdId() != null){
            row.createCell(index++).setCellValue(product.getPdId());
        }
        row.createCell(index++).setCellValue(product.getPdName());
        row.createCell(index++).setCellValue(product.getProducer());
        row.createCell(index++).setCellValue(product.getPrice());
        row.createCell(index++).setCellValue(StringUtil.formatDate(product.getProduceDate(), 0));
        row.createCell(index++).setCellValue(StringUtil.formatDate(product.getExpirationDate(), 0));
        row.createCell(index++).setCellValue(product.getRemain());
        row.createCell(index++).setCellValue(StringUtil.formatDate(product.getAddDate(), 0));
        row.createCell(index++).setCellValue(product.getRemark());
    }

    // 获取最新的商品编号
    @RequestMapping("/selectNewPdId")
    public Object selectNewPdId(){
        String newPdId = productService.selectNewPdId();
        Map<String, Object> map = new HashMap<>();
        map.put("pdId", newPdId);
        return map;
    }
}
