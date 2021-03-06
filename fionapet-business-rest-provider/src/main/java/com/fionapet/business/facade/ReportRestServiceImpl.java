package com.fionapet.business.facade;

import com.fionapet.business.entity.ReportByItemDayVO;
import com.fionapet.business.entity.ReportByItemMonthVO;
import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import com.fionapet.business.service.ReportService;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.facade.RestServiceBase;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import java.util.List;
import java.util.Map;

/**
 * 报表统计
* Created by tom on 2016-07-18 11:56:10.
 */
public class ReportRestServiceImpl extends RestServiceBase<ReportByPersonVO> implements ReportRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportRestServiceImpl.class);
    @org.springframework.beans.factory.annotation.Autowired
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
    public RestResult<List<ReportByPersonVO>> person(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.person(year,month,day));
    }

    @Override
    public RestResult<Map<String, List>> doctor(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("user") String user) {
        return RestResult.OK(reportService.doctor(year,month,user));
    }

    @Override
    public RestResult<Map<String, List>> doctorInHospital(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("user") String user) {
        return RestResult.OK(reportService.doctorInHospital(year,month,user));
    }

    @Override
    public RestResult<List<String[]>> gestPaidAction(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.gestPaidAction(year,month,day));
    }

    @Override
    public RestResult<List<ReportByItemDayVO>> itemDay(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.itemDay(year,month,day));
    }

    @Override
    public RestResult<List<ReportByItemMonthVO>> itemMonth(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month) {
        return RestResult.OK(reportService.itemMonth(year,month));
    }

    @Override
    public RestResult<List<String[]>> medicRegisterRecord(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.medicRegisterRecord(year,month,day));
    }

    @Override
    public RestResult<Map<String, Long>> gestVip(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.gestVip(year,month,day));
    }

    @Override
    public RestResult<Map<String, String>> foster(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("year") String year, @ApiParam("month") String month, @ApiParam("day") String day) {
        return RestResult.OK(reportService.foster(year,month,day));
    }
}
