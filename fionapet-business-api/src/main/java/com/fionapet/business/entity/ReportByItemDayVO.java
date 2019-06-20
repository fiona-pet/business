package com.fionapet.business.entity;

import org.dubbo.x.entity.Idable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * Created by tom on 16/10/23.
 */
@Entity
@Table(name = "v_report_by_item")
public class ReportByItemDayVO extends ReportByItemVO{

}
