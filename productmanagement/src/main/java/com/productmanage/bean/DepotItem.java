package com.productmanage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// depot item message - 补给信息
@Data
public class DepotItem implements Serializable {
    private String itemId;      // 补给编号
    private String depotId;     // 补给员编号
    private String pdId;        // 需要补给的商品的编号
    private Date depotDate;     // 补给日期
    private Date addDate;       // 添加日期
    private Integer depotRemain;    // 补给数量

    public DepotItem(){}

    public DepotItem(String itemId, String depotId, String pdId, Date depotDate, Date addDate, Integer depotRemain) {
        this.itemId = itemId;
        this.depotId = depotId;
        this.pdId = pdId;
        this.depotDate = depotDate;
        this.addDate = addDate;
        this.depotRemain = depotRemain;
    }

    public DepotItem(String depotId, String pdId, Date depotDate, Date addDate) {
        this.depotId = depotId;
        this.pdId = pdId;
        this.depotDate = depotDate;
        this.addDate = addDate;
    }
}
