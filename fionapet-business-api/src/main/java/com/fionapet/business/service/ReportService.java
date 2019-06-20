package com.fionapet.business.service;

import com.fionapet.business.entity.ReportByItemDayVO;
import com.fionapet.business.entity.ReportByItemMonthVO;
import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import org.dubbo.x.service.CURDService;

import java.util.List;
import java.util.Map;

/**
 * 报表统计
* Created by tom on 2016-07-18 11:56:10.
 */
public interface ReportService extends CURDService<ReportByPersonVO> {

    List<ReportByPersonVO> person(String year,String month, String day);

    List<ReportByItemMonthVO> itemMonth(String year, String month);

    List<ReportByItemDayVO> itemDay(String year, String month, String day);

    List<String[]> gestPaidAction(String year,String month, String day);

    List<String[]> medicRegisterRecord(String year,String month, String day);

    Map<String, Long> gestVip(String year,String month, String day);

    Map<String, List> doctor(String year,String month, String user);

    Map<String, List> doctorInHospital(String year,String month, String user);

    Map<String, String> foster(String year,String month, String day);
}
