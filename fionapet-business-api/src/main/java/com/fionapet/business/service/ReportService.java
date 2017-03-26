package com.fionapet.business.service;

import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import org.dubbo.x.service.CURDService;

import java.util.List;

/**
 * 报表统计
* Created by tom on 2016-07-18 11:56:10.
 */
public interface ReportService extends CURDService<ReportByPersonVO> {

    List<ReportByPersonVO> person(String month);

    List<ReportByItemVO> item(String month);
}
