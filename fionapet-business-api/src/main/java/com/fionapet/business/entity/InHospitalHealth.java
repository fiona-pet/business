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
public class InHospitalHealth extends CMSPEntity {
    
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
     * 体重
     */
    @ApiModelProperty(value = "体重")
    @Column(columnDefinition = "体重")
    private float weight;
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * 温度
     */
    @ApiModelProperty(value = "温度", required = false)

    @Column(columnDefinition = "温度")
    private String temperature;
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 临床检查
     */
    @ApiModelProperty(value = "临床检查", required = false)

    @Column(columnDefinition = "临床检查")
    private String rheme;
    public String getRheme() {
        return rheme;
    }
    public void setRheme(String rheme) {
        this.rheme = rheme;
    }


    /**
     * 住院情况
     */
    @ApiModelProperty(value = "住院情况", required = false)

    @Column(columnDefinition = "住院情况")
    private String diagnosed;
    public String getDiagnosed() {
        return diagnosed;
    }
    public void setDiagnosed(String diagnosed) {
        this.diagnosed = diagnosed;
    }

    /**
     * 医生意见
     */
    @ApiModelProperty(value = "医生意见", required = false)

    @Column(columnDefinition = "医生意见")
    private String doctorAdvice;
    public String getDoctorAdvice() {
        return doctorAdvice;
    }
    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
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

