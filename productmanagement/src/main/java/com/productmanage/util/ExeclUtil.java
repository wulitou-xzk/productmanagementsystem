package com.productmanage.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class ExeclUtil {

    private static HSSFWorkbook workbook = null;

    // 导出数据时创建表格及表格头行
    public static HSSFSheet createSheet(String sheetName, String[] header){
        // 每次创建表格时重新实例化 HSSFWorkbook 对象，防止出现sheet重名问题
        workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFRow headRow = sheet.createRow(0);   // 创建表格中的头行
        headRow.setHeight((short) (23.50 * 20)); // 设置行高
        for (int j = 0; j < header.length; j++) {
            headRow.createCell(j).setCellValue(header[j]);
            setDefaultAutoSizeColumn(sheet, null, j);
        }
        return sheet;
    }

    // 设置表格单元格自适应，是表格更为美观
    public static void setDefaultAutoSizeColumn(HSSFSheet sheet, HSSFRow row, int...column){
        if(row != null){
            row.setHeight((short) (20.5 * 20));
        }
        for(int i : column){
            // 调整每一列宽度
            sheet.autoSizeColumn((short) i);
            // 解决自动设置列宽中文失效的问题
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
        }
    }

    // 收尾处理
    public static void endStep(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition","attachment;filename=" + fileName + ".xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    // 检验是否为execl2003，如果是就返回true
    public static boolean isExecl2003(String fileName){
        if(!fileName.endsWith(".xls")){
            return false;
        }
        return true;
    }

    // 检验是否为execl2007，如果是就返回true
    public static boolean isExecl2007(String fileName){
        if(!fileName.endsWith(".xlsx")){
            return false;
        }
        return true;
    }

    // 判断文件是不是表格
    public static boolean isExecl(String fileName){
        if(fileName == null || "".equals(fileName) || !isExecl2003(fileName) || isExecl2007(fileName)){
            return false;
        }
        return true;
    }

    // 导入数据时根据导入的表格进行创建Sheet对象
    public static Sheet createSheet(String fileName, MultipartFile file) throws Exception {
        InputStream is = file.getInputStream();
        Workbook workbook = null;
        if(ExeclUtil.isExecl2003(fileName)){
            workbook = new HSSFWorkbook(is);
        }
        if(ExeclUtil.isExecl2007(fileName)){
//            workbook = new XSSFWorkbook(is);
            return null;
        }
        Sheet sheet = workbook.getSheetAt(0);
        return sheet;
    }
}
