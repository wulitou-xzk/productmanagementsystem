package com.productmanage.service;

import com.productmanage.bean.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationService {
    String insertNotification(Notification notification);

    int deleteNotificationById(String notifId);

    int deleteByUid(String uId);

    int deleteByPdId(String pdId);

    Notification updateNotification(Notification notification);

    Notification updateFinishDate(String notifId);

    String selectNewNotifId();

    Notification selectNotificationById(String notifId);

    List<Notification> selectNotificationByPage(String sendUid, String acceptUid, String operation, Integer pageIndex, Integer pageSize,
                                                String sortField, String sortOrder, Integer self);


    int findLength(String sendUid, String acceptUid, String operation);
}
