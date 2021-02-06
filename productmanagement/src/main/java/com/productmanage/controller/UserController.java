package com.productmanage.controller;

import com.alibaba.fastjson.JSONArray;
import com.productmanage.bean.User;
import com.productmanage.serviceimpl.*;
import com.productmanage.util.ExeclUtil;
import com.productmanage.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 仅管理员可操作
 * 新增、删除、查询、修改用户（员工）
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private DepotItemServiceImpl depotItemService;
    @Autowired
    private TurnOverServiceImpl turnOverService;
    @Autowired
    private LoginDetailServiceImpl detailService;

    // 新增一个用户（员工）
    @RequestMapping("/insertUser")
    public Object insertUser(String userJSON){
        String json = userJSON.replace("[","").replace("]","");
        User user = (User) JSONArray.parseObject(json, User.class);
        String result = userService.insertUser(user);
        return result;
    }

    @RequestMapping("/insertUserBySheet")
    public Object insertUserBySheet(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String result = batchImport(fileName, file);
        return result;
    }

    // 获取导入结果
    private String batchImport(String fileName, MultipartFile file) throws Exception {
        if(!ExeclUtil.isExecl(fileName)){
            return "上传文件格式不正确，请使用xls文件";
        }
        StringBuffer insertFail = new StringBuffer("");
        Sheet sheet = ExeclUtil.createSheet(fileName, file);
        if(sheet == null){
            return "文件中没有表格或者您使用了xlsx文件格式，请使用xls文件";
        }
        for(int i = 1; i <= sheet.getLastRowNum(); i++){
            StringBuffer fail = new StringBuffer("第" + (i + 1) + "行员工：");
            Row row = sheet.getRow(i);
            if(row == null) {
                continue;
            }
            Object obj = checkDataFromExcel(row, fail);
            if(obj instanceof User){
                userService.insertUser((User) obj);
                continue;
            }
            insertFail.append(fail + "；<br>");
        }
        return insertFail.length() == 0 ? "所有员工添加成功！！！" : insertFail.append("其余员工添加成功").toString();
    }

    // 校验表格中的数据
    private Object checkDataFromExcel(Row row, StringBuffer fail){
        int cellIndex = 0;
        String userName = null;     // 员工姓名
        Double level = null;		// 表格中获取的员工等级
        Integer userLevel = null;	// 员工等级
        Date addDate = null;		// 员工入职日期
        Double phone1 = null;		// 表格中获取的员工联系电话
        String phone = null;		// 员工联系电话
        String remark = null;		// 备注

        try{
            userName = row.getCell(cellIndex++).getStringCellValue().trim();
            userName.substring(1);
            level = row.getCell(cellIndex++).getNumericCellValue();
            userLevel = level.intValue();
            addDate = row.getCell(cellIndex++).getDateCellValue();
            phone1 = row.getCell(cellIndex++).getNumericCellValue();
            phone = String.format("%.0f", phone1);
            remark = row.getCell(cellIndex++).getStringCellValue().trim();
        }catch(Exception e){
            return getFailString(cellIndex - 1, fail);
        }
        String uId = userService.selectNewUid(userLevel);
        User user = new User(uId, userName, userLevel, remark, addDate, phone);
        return user;
    }

    // 通过index判断是哪种错误
    private StringBuffer getFailString(int index, StringBuffer fail){
        // index ==> 0 / 1 / 2 / 3
        switch (index){
            case 0: fail.append("姓名未填写，"); break;
            case 1: fail.append("等级未正确填写，"); break;
            case 2: fail.append("入职日期未正确填写，"); break;
            case 3: fail.append("联系方式未正确填写，"); break;
            default:break;
        }
        return fail;
    }

    // 删除一个员工，需要级联删除登录记录表、货物补给表、通知表中的相关数据
    // id为需要删除的员工编号，uId为当前管理员的编号
    @RequestMapping("/delUser")
    public Object deleteUserById(String id, String uId){
        String[] Ids = id.split(",");
        StringBuffer delFail = new StringBuffer("");
        StringBuffer delSuccess = new StringBuffer("");
        for (String uid : Ids ) {
            int isDel = 0;  // 用于判断是否删除成功
            String userName = userService.selectUserById(uId).getUserName();
            // 1.防止管理员将自己删除
            // 2.防止员工在登录状态时被删除
            if(!uId.equals(uid) && detailService.isLogin(uid) == null) {
                detailService.deleteLoginDetailByUid(uid);
                depotItemService.deleteDepotIdByDepotId(uid);
                turnOverService.updateUidByDelUser(uid);
                notificationService.deleteByUid(uid);
                isDel = userService.deleteUserById(uid);
            }
            if (isDel > 0) {
                delSuccess.append(userName + ",");
            }
            if (isDel <= 0) {
                delFail.append(userName + ",");
            }
        }
        return StringUtil.getResultString(delFail, delSuccess);
    }

    // 管理员编辑用户信息（用户名）
    @RequestMapping("/editUserByAdmin")
    public Object updateUserByAdmin(String userJSON){
        String json = userJSON.replace("[","").replace("]","");
        User user = (User) JSONArray.parseObject(json, User.class);
        if(userService.updateUserByAdmin(user) == null){
            return "员工信息更新失败";
        }
        return "员工信息更新成功";
    }

    // 用户操作（找回密码、修改电话、修改密码）
    @RequestMapping("/operationByUser")
    public Object operationByUser(String userJSON, String uId, String detailId){
        String json = userJSON.replace("[","").replace("]","");
        User user = (User) JSONArray.parseObject(json, User.class);
        // 找回密码
        if(user.getUserLevel() != null){
            return userService.selectPwd(user);
        }
        // 修改预留电话
        if(user.getPassword() == null && user.getUserLevel() == null){
            User user1 = userService.editPhoneByUser(user);
            if(user1 == null){
                return "预留电话修改失败，请检查工号/姓名/原预留电话是否无误";
            }else{
                return "预留电话已修改为：" + user1.getPhone();
            }
        }
        // 修改密码
        if(user.getPassword() != null){
            if(userService.editPwdByUser(user, uId, detailId) == null){
                return "密码修改失败，检查工号/姓名/原密码是否正确/两次输入的新密码是否一致";
            }else{
                return "密码修改成功，即将跳转登录页重新登录";
            }
        }
        return "";
    }

    // 根据用户编号查询用户信息
    @RequestMapping("/selUserById")
    public Object selectUserById(String uId){
        User user = userService.selectUserById(uId.trim());
        return user;
    }

    // 获取多条用户信息，可根据用户等级模糊查询
    @RequestMapping("/selUserByPage")
    public Object selectUserByPage(String userName, String sortField, String sortOrder, Integer userLevel, Integer pageIndex, Integer pageSize){
        List<User> userList = userService.selectUserByPage(userName, sortField, sortOrder, userLevel, pageIndex, pageSize);
        int total = userService.findLength(userName, userLevel);
        Map<String, Object> map = new HashMap<>();
        map.put("data", userList);
        map.put("total", total);
        return map;
    }

    @RequestMapping("/selectUserBySheet")
    public void selectUserBySheet(HttpServletResponse response) throws IOException {
        int i = 1;
        String fileName = "employeeInfo";
        String[] header = {"员工编号","员工姓名","员工职位","入职日期","联系方式","备注"};
        HSSFSheet sheet = ExeclUtil.createSheet("员工信息",header);
        List<User> userList = userService.selectUserBySheet();
        for (User user : userList) {
            HSSFRow row = sheet.createRow(i++);
            setRow(row, user, 0);
            ExeclUtil.setDefaultAutoSizeColumn(sheet, row, 0, 1, 2, 3, 4, 5);
        }
        ExeclUtil.endStep(response, fileName);
    }

    @RequestMapping("/getFileDemo")
    public void getFileDemo(HttpServletResponse response) throws IOException {
        int i = 0;
        String fileName = "employeeInfoDemo";
        String[] header = {"姓名","级别","入职日期","电话","备注"};
        HSSFSheet sheet = ExeclUtil.createSheet("员工信息", header);
        HSSFRow row = sheet.createRow(1);
        User user = new User("张三", 3, "", new Date(), "18279681235");
        user.setRemark("可填入一些额外信息（选填）（说明：本文件中的数据仅用于示范，" +
                "导入系统前需将本行数据删除，并根据格式添加新的数据，否则将无法添加）");
        setRow(row, user, 0);
        ExeclUtil.setDefaultAutoSizeColumn(sheet, row, 0, 1, 2, 3, 4);
        ExeclUtil.endStep(response, fileName);
    }

    private void setRow(HSSFRow row, User user, int index){
        String[] userLevel = {"补给员","清点员","管理员", "只能填入0、1、2，分别代表：补给员、清点员、管理员"};
        if(user.getUId() != null){
            row.createCell(index++).setCellValue(user.getUId());
        }
        row.createCell(index++).setCellValue(user.getUserName());
        row.createCell(index++).setCellValue(userLevel[user.getUserLevel()]);
        row.createCell(index++).setCellValue(StringUtil.formatDate(user.getAddDate(),0));
        row.createCell(index++).setCellValue(user.getPhone());
        row.createCell(index++).setCellValue(user.getRemark());
    }

    // 获取所有用户的编号以及用户名
    @RequestMapping("/selectUserIdName")
    public Object selectUserIdName(String type){
        List<User> userList = userService.selectUserIdName(type);
        return userList;
    }

    // 获取最新的用户编号
    @RequestMapping("/selNewUid")
    public Object selNewUid(Integer userLevel){
        String newUid = userService.selectNewUid(userLevel);
        Map<String, Object> map = new HashMap<>();
        map.put("uId", newUid);
        return map;
    }
}