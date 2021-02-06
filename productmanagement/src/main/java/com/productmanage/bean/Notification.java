package com.productmanage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// notification - 通知信息
@Data
public class Notification implements Serializable {
    private String notifId;     // 通知编号
    private String sendUid;     // 发送通知的员工的编号
    private String acceptUid;   // 执行通知的员工编号
    private String pdId;        // 通知对应的商品编号
    private String operation;   // 通知内容
    private Date notifiDate;    // 通知日期
    private int state;          // 通知完成状态 0 / 1
    private Date finishDate;    // 通知完成时间

    public Notification(){}

    public Notification(String sendUid, String acceptUid, String pdId, String operation, Date notifiDate, int state, Date finishDate) {
        this.sendUid = sendUid;
        this.acceptUid = acceptUid;
        this.pdId = pdId;
        this.operation = operation;
        this.notifiDate = notifiDate;
        this.state = state;
        this.finishDate = finishDate;
    }

    public Notification(String notifId, String sendUid, String acceptUid, String pdId, String operation, Date notifiDate, int state, Date finishDate) {
        this.notifId = notifId;
        this.sendUid = sendUid;
        this.acceptUid = acceptUid;
        this.pdId = pdId;
        this.operation = operation;
        this.notifiDate = notifiDate;
        this.state = state;
        this.finishDate = finishDate;
    }
}
