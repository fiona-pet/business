package com.fionapet.business.facade;

import com.fionapet.business.entity.InHospitalRecord;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.InHospitalRecordService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;

/**
 * 医院记录表
* Created by tom on 2016-07-25 09:32:32.
 */
public class InHospitalRecordRestServiceImpl extends RestServiceBase<InHospitalRecord> implements InHospitalRecordRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InHospitalRecordRestServiceImpl.class);

    private InHospitalRecordService inHospitalRecordService;

    public InHospitalRecordService getInHospitalRecordService() {
        return inHospitalRecordService;
    }

    public void setInHospitalRecordService(InHospitalRecordService inHospitalRecordService) {
        this.inHospitalRecordService = inHospitalRecordService;
    }

    @Override
    public CURDService<InHospitalRecord> getService() {
        return inHospitalRecordService;
    }

    @Override
    public RestResult<InHospitalRecord> create(@HeaderParam("authorization") String token, InHospitalRecord entity) {
        if (null != entity.getId()){
            InHospitalRecord inHospitalRecord = getService().detail(entity.getId());
            entity.setInputMoney(inHospitalRecord.getInputMoney());
        }
        return super.create(token, entity);
    }

    @Override
    public RestResult<InHospitalRecord> over(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("inHospitalRecordCode") String inHospitalRecordCode) {
        return RestResult.OK(((InHospitalRecordService)getService()).over(inHospitalRecordCode));
    }
}
