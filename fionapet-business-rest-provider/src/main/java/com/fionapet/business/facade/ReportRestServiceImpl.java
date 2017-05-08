package com.fionapet.business.facade;

import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import com.fionapet.business.service.AppConfigService;
import com.fionapet.business.service.ReportService;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.facade.RestServiceBase;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Map;

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
    public RestResult<List<ReportByPersonVO>> person(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.person(month,day));
    }

    @Override
    public RestResult<Map<String, List>> doctor(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") String month, @ApiParam("user") String user) {
        return RestResult.OK(reportService.doctor(month,user));
    }

    @Override
    public RestResult<List<String[]>> gestPaidAction(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.gestPaidAction(month,day));
    }

    @Override
    public RestResult<List<ReportByItemVO>> item(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.item(month,day));
    }

    @Override
    public RestResult<List<String[]>> medicRegisterRecord(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.medicRegisterRecord(month,day));
    }

    @Override
    public RestResult<Map<String, Long>> gestVip(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.gestVip(month,day));
    }
}
