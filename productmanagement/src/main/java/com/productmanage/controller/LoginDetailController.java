package com.productmanage.controller;

import com.alibaba.fastjson.JSONArray;
import com.productmanage.bean.LoginDetail;
import com.productmanage.bean.User;
import com.productmanage.service.UserService;
import com.productmanage.serviceimpl.LoginDetailServiceImpl;
import com.productmanage.util.ExeclUtil;
import com.productmanage.util.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 仅管理员可操作
 * 新增（用户登录）、删除、查询登录记录
 */
@RequestMapping("/logindetail")
@RestController
public class LoginDetailController {

    @Autowired
    private LoginDetailServiceImpl loginDetailService;
    @Autowired
    private UserService userService;

    // 用户登录时自动添加登录记录
    @RequestMapping("/login")
    public Object login(String userJSON){
        String json = userJSON.replace("[","").replace("]","");
        User user = (User) JSONArray.parseObject(json, User.class);
        String uId = user.getUId();
        String detailId = loginDetailService.selectNewDetailId();
        LoginDetail detail = new LoginDetail(detailId, uId, new Date());
        User loginUser = loginDetailService.insertLoginDetail(detail,user);
        if(loginUser != null){
            return detailId + "-" + loginUser.getUserLevel();
        }else{
            return "fail";
        }
    }

    // 用户退出时更新退出时间
    @RequestMapping("/logout")
    public Object logout(String uId, String detailId){
        String result = loginDetailService.updateLoginDetail(uId, detailId);
        return result;
    }

    // 可一次删除一条或多条登录记录
    @RequestMapping("/deleteLoginDetailById")
    public Object deleteLoginDetailById(String id){
        String[] Ids = id.split(",");
        StringBuffer delFail = new StringBuffer("");
        StringBuffer delSuccess = new StringBuffer("");
        for(String detailId : Ids){
            int isDel = loginDetailService.deleteLoginDetailById(detailId);
            if(isDel > 0){
                delSuccess.append(detailId + ",");
            }
            if(isDel <= 0){
                delFail.append(detailId + ",");
            }
        }
        return StringUtil.getResultString(delFail, delSuccess);
    }

    // 根据记录编号检验是否被强制下线
    @RequestMapping("/selectExitLoginById")
    public Object selectExitLoginById(String detailId){
        String exitLogin = loginDetailService.selectExitLoginById(detailId);
        return exitLogin;
    }

    // 管理员强制用户下线，并修改退出时间
    @RequestMapping("/quitByAdmin")
    public Object quitByAdmin(String detailId){
        String result = loginDetailService.quitByAdmin(detailId);
        return result;
    }

    // 根据记录编号查询记录
    @RequestMapping("/selectLoginDetailById")
    public Object selectLoginDetailById(String detailId){
        LoginDetail detail = loginDetailService.selectLoginDetailById(detailId);
        Map<String, Object> map = getMap(detail);
        return map;
    }

    // 获取多条登录记录，可根据用户编号进行模糊查询
    @RequestMapping("/selectLoginDetailByPage")
    public Object selectLoginDetailByPage(String uId, String sortField, String sortOrder, String date,
                                          Integer pageIndex, Integer pageSize){
        List<LoginDetail> loginDetailList = loginDetailService.selctLoginDetailByPage(uId, sortField, sortOrder, date, pageIndex, pageSize);
        List<Map<String, Object>> newList = new ArrayList<>();
        int total = loginDetailService.findLength(uId, date);
        for(LoginDetail detail : loginDetailList){
            Map<String, Object> map = getMap(detail);
            newList.add(map);
        }
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("data", newList);
        newMap.put("total", total);
        return newMap;
    }

    // 将数据库中的记录以表格导出
    @RequestMapping("/logindetailTableFile")
    public void logindetailTableFile(HttpServletResponse response) throws IOException {
        int i = 1;
        String fileName = "logindetail";
        List<LoginDetail> detailList = loginDetailService.selectLoginDetailBySheet();
        String[] header = {"登录详情编号", "员工编号", "登录时间", "退出时间"};
        HSSFSheet sheet = ExeclUtil.createSheet("登录记录", header);
        for (LoginDetail detail : detailList) {
            HSSFRow row = sheet.createRow(i++);
            row.createCell(0).setCellValue(detail.getDetailId());
            row.createCell(1).setCellValue(detail.getUId());
            row.createCell(2).setCellValue(StringUtil.formatDate(detail.getCurrentLogin(), 1));
            row.createCell(3).setCellValue(StringUtil.formatDate(detail.getExitLogin(), 1));
            ExeclUtil.setDefaultAutoSizeColumn(sheet, row, 0, 1, 2, 3);
        }
        ExeclUtil.endStep(response, fileName);
    }

    private Map<String, Object> getMap(LoginDetail detail){
        Map<String, Object> map = new HashMap<>();
        String userName = userService.selectUserById(detail.getUId()).getUserName();
        map.put("detailId",detail.getDetailId());
        map.put("uId",detail.getUId());
        map.put("currentLogin",detail.getCurrentLogin());
        map.put("exitLogin",detail.getExitLogin());
        map.put("userName",userName);
        return map;
    }

}
