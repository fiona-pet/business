package com.fionapet.business.entity;

/**
 * 缺货订单
 * Created by tom on 2017/6/18.
 */
public class OrderVO {
    public OrderVO(String itemCode, String itemName, String itemBulk, String itemCountNum, String scatteredCountNum, String dealerName, String mobilePhone, String telPhone, String updateDate) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemBulk = itemBulk;
        this.itemCountNum = itemCountNum;
        this.scatteredCountNum = scatteredCountNum;
        this.dealerName = dealerName;
        this.mobilePhone = mobilePhone;
        this.telPhone = telPhone;
        this.updateDate = updateDate;
    }

    /**
     * 编号
     */
    private String itemCode;

    /**
     * 名称
     */
    private String itemName;

    /**
     *零散比
     */
    private String itemBulk;

    /**
     *正装数量
     */
    private String itemCountNum;
    /**
     *散装数量
     */
    private String scatteredCountNum;
    /**
     *经销商
     */
    private String dealerName;
    /**
     *手机
     */
    private String mobilePhone;
    /**
     *电话
     */
    private String telPhone;
    /**
     *更新日期
     */
    private String updateDate;

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

    public String getItemBulk() {
        return itemBulk;
    }

    public void setItemBulk(String itemBulk) {
        this.itemBulk = itemBulk;
    }

    public String getItemCountNum() {
        return itemCountNum;
    }

    public void setItemCountNum(String itemCountNum) {
        this.itemCountNum = itemCountNum;
    }

    public String getScatteredCountNum() {
        return scatteredCountNum;
    }

    public void setScatteredCountNum(String scatteredCountNum) {
        this.scatteredCountNum = scatteredCountNum;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
