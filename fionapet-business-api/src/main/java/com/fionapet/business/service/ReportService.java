package com.fionapet.business.service;

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

    List<ReportByPersonVO> person(String month, String day);

    List<ReportByItemVO> item(String month, String day);

    List<String[]> gestPaidAction(String month, String day);

    List<String[]> medicRegisterRecord(String month, String day);

    Map<String, Long> gestVip(String month, String day);

    Map<String, List> doctor(String month, String user);
}
