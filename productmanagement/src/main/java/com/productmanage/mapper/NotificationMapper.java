package com.productmanage.mapper;

import com.productmanage.bean.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface NotificationMapper {
    int insertNotification(Notification notification);

    int deleteNotificationById(@Param("notifId")String notifId);

    int deleteByUid(@Param("uId")String uId);

    int deleteByPdId(@Param("pdId")String pdId);

    int updateNotification(Notification notification);

    int updateNotificationFinishDate(@Param("notifId")String notifId, @Param("finishDate")Date finishDate);

    String selectNewNotifId();

    Notification selectNotificationById(@Param("notifId")String notifId);

    List<Notification> selectNotificationByPage(@Param("sendUid")String sendUid, @Param("acceptUid")String acceptUid, @Param("operation")String operation, @Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize,
    		@Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("self")Integer self);

    int findLength(@Param("sendUid")String sendUid, @Param("acceptUid")String acceptUid, @Param("operation")String operation);
}
