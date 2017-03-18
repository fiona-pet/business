package com.fionapet.business.service;

import com.fionapet.business.entity.ReportByPersonVO;
import com.fionapet.business.repository.ReportDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  报表统计
* Created by tom on 2016-07-18 11:56:10.
 */
public class ReportServiceImpl extends CURDServiceBase<ReportByPersonVO> implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Override
    public DaoBase<ReportByPersonVO> getDao() {
        return reportDao;
    }

    @Override
    public List<ReportByPersonVO> person(String month) {
        return reportDao.findCreateDateBetween("2017-03-01", "2017-03-31");
    }
}
