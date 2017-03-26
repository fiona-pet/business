package com.fionapet.business.service;

import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import com.fionapet.business.repository.ReportByItemDao;
import com.fionapet.business.repository.ReportDao;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

/**
 *  报表统计
* Created by tom on 2016-07-18 11:56:10.
 */
public class ReportServiceImpl extends CURDServiceBase<ReportByPersonVO> implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ReportByItemDao reportByItemDao;
    @Override
    public DaoBase<ReportByPersonVO> getDao() {
        return reportDao;
    }

    @Override
    public List<ReportByPersonVO> person(String month) {
        String date = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM");
        return reportDao.findCreateDateBetween(date + "-01", date + "-31");
    }

    @Override
    public List<ReportByItemVO> item(String month) {
        String date = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM");
        return reportByItemDao.findByCreateDateOrderByTotalDesc(date);
    }
}
