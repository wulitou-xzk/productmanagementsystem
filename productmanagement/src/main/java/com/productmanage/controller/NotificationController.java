package com.productmanage.controller;

import com.alibaba.fastjson.JSONArray;
import com.productmanage.bean.Notification;
import com.productmanage.service.ProductService;
import com.productmanage.service.UserService;
import com.productmanage.serviceimpl.NotificationServiceImpl;
import com.productmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员可执行所有操作；清点员、补给员仅可执行新增操作
 * 新增（发送通知）、删除、修改（完成通知、修改通知内容）、查询通知
 * 页面显示时，将对应的uid、pdid映射为对应的username、pdname
 */
@RequestMapping("/notification")
@RestController
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    // 添加一条通知
    @RequestMapping("/insertNotification")
    public Object insertNotification(String notificationJSON){
        String json = notificationJSON.replace("[","").replace("]","");
        Notification notification = (Notification)JSONArray.parseObject(json,Notification.class);
        notification.setState(0);
        String result = notificationService.insertNotification(notification);
        return result;
    }

    // 一次仅可删除一条通知
    @RequestMapping("/deleteNotificationById")
    public Object deleteNotificationById(String id){
        String[] ids = id.split(",");
        StringBuffer delFail = new StringBuffer("");
        StringBuffer delSuccess = new StringBuffer("");
        for(String notifiId : ids){
            int isDel = notificationService.deleteNotificationById(notifiId);
            if(isDel > 0){
                delSuccess.append(notifiId + ",");
            }else{
                delFail.append(notifiId + ",");
            }
        }
        return StringUtil.getResultString(delFail, delSuccess);
    }

    // 更新通知内容
    @RequestMapping("/editNotification")
    public Object updateNotification(String notificationJSON){
        String json = notificationJSON.replace("[","").replace("]","");
        Notification notification = (Notification)JSONArray.parseObject(json,Notification.class);
        if(notificationService.updateNotification(notification) == null){
            return "修改通知失败";
        }
        return "成功更新通知";
    }

    // 更新通知完成时间
    @RequestMapping("/updateFinishDate")
    public Object updateFinishDate(String notifId){
        if(notificationService.updateFinishDate(notifId) == null){
            return "操作失败";
        }
        return "操作成功";
    }

    // 根据通知编号查询通知内容
    @RequestMapping("selectNotificationById")
    public Object selectNotificationById(String notifId){
        Notification notification = notificationService.selectNotificationById(notifId);
        Map<String, Object> map = getMap(notification);
        return map;
    }

    // 获取多条通知，可根据通知内容进行模糊查询
    @RequestMapping("/selectNotificationByPage")
    public Object selectNotificationByPage(String sendUid, String acceptUid, String operation, Integer pageIndex, Integer pageSize,
                                           String sortField, String sortOrder, Integer self){
        List<Notification> notificationList = notificationService.selectNotificationByPage(sendUid, acceptUid, operation, pageIndex, pageSize, sortField, sortOrder, self);
        List<Map<String, Object>> newList = new ArrayList<>();
        int total = notificationList.size();
        for(Notification notification : notificationList){
            Map<String, Object> map = getMap(notification);
            newList.add(map);
        }
        Map<String, Object> newMap = new HashMap<>();
        newMap.put("data", newList);
        newMap.put("total", total);
        return newMap;
    }

    private Map<String, Object> getMap(Notification notification){
        Map<String, Object> map = new HashMap<>();
        String sendUser = userService.selectUserById(notification.getSendUid().trim()).getUserName();
        String acceptUser = userService.selectUserById(notification.getAcceptUid().trim()).getUserName();
        String pdId = notification.getPdId();
        String pdName = "---";
        if(pdId == null || "".equals(pdId)){
            pdId = "---";
        }else{
            pdName = productService.selectProductById(pdId).getPdName();
        }
        map.put("notifId",notification.getNotifId());
        map.put("acceptUid",notification.getAcceptUid());
        map.put("acceptUser",acceptUser);
        map.put("sendUid", notification.getSendUid());
        map.put("sendUser",sendUser);
        map.put("pdId",pdId);
        map.put("pdName",pdName);
        map.put("operation",notification.getOperation());
        map.put("notifiDate",notification.getNotifiDate());
        map.put("finishDate",notification.getFinishDate());
        map.put("state",notification.getState());
        return map;
    }

    // 获取一条最新的通知编号
    @RequestMapping("/selectNewNotifId")
    public Object selectNewNotifId(){
        String newNotifId = notificationService.selectNewNotifId();
        Map<String, Object> map = new HashMap<>();
        map.put("notifId", newNotifId);
        return map;
    }
}
