package com.productmanage.serviceimpl;

import com.productmanage.bean.Notification;
import com.productmanage.mapper.NotificationMapper;
import com.productmanage.service.NotificationService;
import com.productmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    // 添加一条通知
    @Override
    public String insertNotification(Notification notification) {
        if(notificationMapper.insertNotification(notification) > 0){
            return "成功添加通知";
        }
        return "添加通知失败";
    }

    // 删除一条/多条通知
    @Override
    @CacheEvict(value = "notification", key = "#notifId")
    public int deleteNotificationById(String notifId) {
        return notificationMapper.deleteNotificationById(notifId);
    }

    @Override
    public int deleteByUid(String uId) {
        return notificationMapper.deleteByUid(uId);
    }

    @Override
    public int deleteByPdId(String pdId) {
        return notificationMapper.deleteByPdId(pdId);
    }

    // 更新通知内容
    @Override
    @CachePut(value = "notification", key = "#notification.notifId", unless = "#result == null")
    public Notification updateNotification(Notification notification) {
        if(notificationMapper.updateNotification(notification)> 0){
            return notification;
        }
        return null;
    }

    // 更新完成时间
    @Override
    @CachePut(value = "notification", key = "#notifId", unless = "#result == null")
    public Notification updateFinishDate(String notifId) {
        if(notificationMapper.updateNotificationFinishDate(notifId, new Date()) > 0){
            return notificationMapper.selectNotificationById(notifId);
        }
        return null;
    }

    // 获取一条最新的通知编号
    @Override
    public String selectNewNotifId() {
        StringBuffer newNotifId = new StringBuffer(notificationMapper.selectNewNotifId());
        newNotifId.append(StringUtil.getIdStr(1));
        return newNotifId.toString();
    }

    // 获取一条通知信息
    @Override
    @Cacheable(cacheNames = {"notification"}, key = "#notifId", unless = "#result == null")
    public Notification selectNotificationById(String notifId) {
        Notification notification = notificationMapper.selectNotificationById(notifId);
        return notification;
    }

    // 获取多条通知信息，可根据关键字进行模糊查询
    @Override
    public List<Notification> selectNotificationByPage(String sendUid, String acceptUid, String operation, Integer pageIndex, Integer pageSize,
                                                       String sortField, String sortOrder, Integer self) {
        operation = (operation == null || "".equals(operation)) ? "" : operation.trim();
        sendUid = (sendUid == null || "".equals(sendUid)) ? "" : sendUid.trim();
        acceptUid = (acceptUid == null || "".equals(acceptUid)) ? "" : acceptUid.trim();
        pageIndex *= pageSize;
        List<Notification> notificationList = notificationMapper.selectNotificationByPage(sendUid, acceptUid, operation, pageIndex, pageSize, sortField, sortOrder, self);
        return notificationList;
    }

    @Override
    public int findLength(String sendUid, String acceptUid, String operation) {
        operation = (operation == null) ? "" : operation.trim();
        int total = notificationMapper.findLength(sendUid, acceptUid, operation);
        return total;
    }
}
