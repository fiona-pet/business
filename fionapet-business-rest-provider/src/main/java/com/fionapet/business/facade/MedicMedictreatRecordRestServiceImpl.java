package com.fionapet.business.facade;

import com.fionapet.business.entity.MedicMedictreatRecord;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.MedicMedictreatRecordService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;

/**
 * 医生处理记录
* Created by tom on 2016-07-18 11:56:10.
 */
public class MedicMedictreatRecordRestServiceImpl extends RestServiceBase<MedicMedictreatRecord> implements MedicMedictreatRecordRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicMedictreatRecordRestServiceImpl.class);

    private MedicMedictreatRecordService medicMedictreatRecordService;

    public MedicMedictreatRecordService getMedicMedictreatRecordService() {
        return medicMedictreatRecordService;
    }

    public void setMedicMedictreatRecordService(MedicMedictreatRecordService medicMedictreatRecordService) {
        this.medicMedictreatRecordService = medicMedictreatRecordService;
    }

    @Override
    public CURDService<MedicMedictreatRecord> getService() {
        return medicMedictreatRecordService;
    }

    @Override
    public RestResult<MedicMedictreatRecord> payReturnVisit(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") String uuid, @ApiParam("remark") String remark) {
        return RestResult.OK(this.medicMedictreatRecordService.payReturnVisit(uuid, remark));
    }
}
