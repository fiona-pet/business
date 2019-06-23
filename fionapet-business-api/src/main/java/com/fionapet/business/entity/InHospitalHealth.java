package com.fionapet.business.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 养育健康状况表
 * <p>
* Created by tom on 2016-07-25 09:32:32.
 */
@Entity
@Table(name = "t_in_hospital_health")
@ApiModel("住院健康状况表")
public class InHospitalHealth extends CMSEntity {
    
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", required = false)
    
    @Column(columnDefinition = "编号")
    private String inHospitalNo;

    public String getInHospitalNo() {
        return inHospitalNo;
    }

    public void setInHospitalNo(String inHospitalNo) {
        this.inHospitalNo = inHospitalNo;
    }

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明", required = false)
    @Column(columnDefinition = "说明")
    private String remark;
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}

