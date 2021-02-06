package com.productmanage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// 商品销售信息
@Data
public class TurnOver implements Serializable {
    private String turnId;      // 商品售出编号
    private String uId;         // 清点员编号
    private String pdId;        // 售出商品的编号
    private Date saleDate;      // 售出日期
    private int saleRemain;     // 商品卖出数量
    private double saleMoney;   // 商品销售额

    public TurnOver(){}

    public TurnOver(String turnId, String pdId, Date saleDate, int saleRemain) {
        this.turnId = turnId;
        this.pdId = pdId;
        this.saleDate = saleDate;
        this.saleRemain = saleRemain;
    }

    public TurnOver(String turnId, String uId, String pdId, Date saleDate, int saleRemain, double saleMoney) {
        this.turnId = turnId;
        this.uId = uId;
        this.pdId = pdId;
        this.saleDate = saleDate;
        this.saleRemain = saleRemain;
        this.saleMoney = saleMoney;
    }
}
