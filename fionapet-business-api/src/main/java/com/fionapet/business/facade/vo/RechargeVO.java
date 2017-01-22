package com.fionapet.business.facade.vo;

/**
 * Created by tom on 2017/1/22.
 */
public class RechargeVO {
    /**
     * 会员id
     */
    private String gestId;
    /**
     * 源金额
     */
    private Double oldMoney;

    /**
     * 充值后金额
     */
    private Double money;

    public Double getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(Double oldMoney) {
        this.oldMoney = oldMoney;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getGestId() {
        return gestId;
    }

    public void setGestId(String gestId) {
        this.gestId = gestId;
    }
}
