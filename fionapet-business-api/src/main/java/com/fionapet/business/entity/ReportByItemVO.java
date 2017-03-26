package com.fionapet.business.entity;

import org.dubbo.x.entity.Idable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tom on 16/10/23.
 */
@Entity
@Table(name = "v_report_by_item")
public class ReportByItemVO implements Idable {

    @Id
    private String id;
    /**
     * 商品编号
     */
    private String itemCode;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 销售数量
     */
    private Double totalNum;
    /**
     * 销售均价
     */
    private  Double avgPrice;
    /**
     * 销售金额
     */
    private  Double total;
    /**
     * 平均进价
     */
    private  Double avgInputPrice;
    /**
     * 总成本
     */
    private Double totalCost;
    /**
     * 库存
     */
    private Double inventory;
    /**
     * 类型
     */
    private String type;
    /**
     * 销售时间
     */
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Double totalNum) {
        this.totalNum = totalNum;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAvgInputPrice() {
        return avgInputPrice;
    }

    public void setAvgInputPrice(Double avgInputPrice) {
        this.avgInputPrice = avgInputPrice;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "ReportByItemVO{" +
                "id='" + id + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", totalNum=" + totalNum +
                ", avgPrice=" + avgPrice +
                ", total=" + total +
                ", avgInputPrice=" + avgInputPrice +
                ", totalCost=" + totalCost +
                ", inventory=" + inventory +
                ", type='" + type + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
