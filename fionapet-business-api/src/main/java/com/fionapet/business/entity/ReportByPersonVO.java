package com.fionapet.business.entity;

import org.dubbo.x.entity.Idable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tom on 16/10/23.
 */
@Entity
@Table(name = "v_report_by_person")
public class ReportByPersonVO implements Idable {

    @Id
    private String id;
    /**
     * 员工
     */
    private String name;
    /**
     * 统计类型
     */
    private String type;
    /**
     * 统计
     */
    private  Double total;
    /**
     * 销售时间
     */
    private String createDate;

    public ReportByPersonVO() {

    }

    public ReportByPersonVO(String name, String type, Double total) {
        this.name = name;
        this.type = type;
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ReportByPerson{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
