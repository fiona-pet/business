package com.fionapet.business.facade;

import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import com.fionapet.business.service.AppConfigService;
import com.fionapet.business.service.ReportService;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.facade.RestServiceBase;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import java.util.List;

/**
 * 报表统计
* Created by tom on 2016-07-18 11:56:10.
 */
public class ReportRestServiceImpl extends RestServiceBase<ReportByPersonVO> implements ReportRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportRestServiceImpl.class);

    private ReportService reportService;

    public ReportService getReportService() {
        return reportService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public CURDService<ReportByPersonVO> getService() {
        return getReportService();
    }

    @Override
    public RestResult<List<ReportByPersonVO>> person(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token) {
        return RestResult.OK(reportService.person(""));
    }

    @Override
    public RestResult<List<ReportByItemVO>> item(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token) {
        return RestResult.OK(reportService.item(""));
    }
}
