package com.fionapet.business.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

/**
 * 住院处方
 * <p>
* Created by tom on 2016-07-18 15:37:44.
 */
@Entity
@Table(name = "t_in_hospital_prescription")
@ApiModel("住院处方")
public class InHospitalPrescription extends CMSPEntity {
    /**
     * 医生记录编号
     */
    @ApiModelProperty(value = "医生记录编号", required = false)
    
    @Column(columnDefinition = "医生记录编号")
    private String inHospitalNo;
    public String getInHospitalNo() {
        return inHospitalNo;
    }
    public void setInHospitalNo(String inHospitalNo) {
        this.inHospitalNo = inHospitalNo;
    }
    
    /**
     * 记录名称
     */
    @ApiModelProperty(value = "记录名称", required = false)
    
    @Column(columnDefinition = "记录名称")
    private String inHospitalId;
    public String getInHospitalId() {
        return inHospitalId;
    }
    public void setInHospitalId(String inHospitalId) {
        this.inHospitalId = inHospitalId;
    }
    
    /**
     * 开处方编号
     */
    @ApiModelProperty(value = "开处方编号", required = false)
    
    @Column(columnDefinition = "开处方编号")
    private String prescriptionCode;
    public String getPrescriptionCode() {
        return prescriptionCode;
    }
    public void setPrescriptionCode(String prescriptionCode) {
        this.prescriptionCode = prescriptionCode;
    }
    
    /**
     * 开处方费用
     */
    @ApiModelProperty(value = "开处方费用", required = false)
    
    @Column(columnDefinition = "开处方费用")
    private double prescriptionCost;
    public double getPrescriptionCost() {
        return prescriptionCost;
    }
    public void setPrescriptionCost(double prescriptionCost) {
        this.prescriptionCost = prescriptionCost;
    }
    
    /**
     * 宠物主人名称
     */
    @ApiModelProperty(value = "宠物主人名称", required = false)
    
    @Column(columnDefinition = "宠物主人名称")
    private String gestName;
    public String getGestName() {
        return gestName;
    }
    public void setGestName(String gestName) {
        this.gestName = gestName;
    }
    
    /**
     * 宠物名称
     */
    @ApiModelProperty(value = "宠物名称", required = false)
    
    @Column(columnDefinition = "宠物名称")
    private String petName;
    public String getPetName() {
        return petName;
    }
    public void setPetName(String petName) {
        this.petName = petName;
    }
    
    /**
     * 病例编号
     */
    @ApiModelProperty(value = "病例编号", required = false)
    
    @Column(columnDefinition = "病例编号")
    private String sickFileCode;
    public String getSickFileCode() {
        return sickFileCode;
    }
    public void setSickFileCode(String sickFileCode) {
        this.sickFileCode = sickFileCode;
    }
    
    /**
     * 医生
     */
    @ApiModelProperty(value = "医生", required = false)
    
    @Column(columnDefinition = "医生")
    private String doctor;
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
    
    /**
     * 影像状态
     */
    @ApiModelProperty(value = "影像状态", required = false)
    
    @Column(columnDefinition = "影像状态")
    private String paidStatus;
    public String getPaidStatus() {
        return paidStatus;
    }
    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }
    
    /**
     * 影像时间
     */
    @ApiModelProperty(value = "影像时间", required = false)
    
    @Column(columnDefinition = "影像时间")
    private Date paidTime;
    public Date getPaidTime() {
        return paidTime;
    }
    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }
    
}

