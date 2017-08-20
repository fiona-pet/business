package com.fionapet.business.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

/**
 * 宠物
 * <p>
 * Created by tom on 2016-07-19 10:31:06.
 */
@Entity
@Table(name = "v_pet")
@ApiModel("宠物视图")
public class PetView extends PetBase {
    private String mobilePhone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}

