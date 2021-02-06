package com.productmanage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// product message - 商品信息
@Data
public class Product implements Serializable {
    private String pdId;            // 商品编号
    private String pdName;          // 商品名
    private double price;           // 商品价格
    private String producer;        // 生产商
    private Date produceDate;       // 生产日期
    private Date expirationDate;    // 过期时间
    private int remain;             // 商品剩余数量
    private String remark;          // 备注信息
    private Date addDate;           // 添加日期

    public Product(){}

    public Product(String pdId, String pdName, double price, String producer,
                   Date produceDate, Date expirationDate, int remain, String remark, Date addDate) {
        this.pdId = pdId;
        this.remark = remark;
        this.addDate = addDate;
        this.pdName = pdName;
        this.price = price;
        this.producer = producer;
        this.produceDate = produceDate;
        this.expirationDate = expirationDate;
        this.remain = remain;
    }

    public Product(String pdName, double price, String producer,
                   Date produceDate, Date expirationDate, int remain, String remark, Date addDate) {
        this.remark = remark;
        this.addDate = addDate;
        this.pdName = pdName;
        this.price = price;
        this.producer = producer;
        this.produceDate = produceDate;
        this.expirationDate = expirationDate;
        this.remain = remain;
    }
}
