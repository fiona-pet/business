package com.fionapet.business.facade;

import com.fionapet.business.entity.FosterRecord;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.FosterRecordService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;

/**
 * 养育记录主表
* Created by tom on 2016-07-25 09:32:32.
 */
public class FosterRecordRestServiceImpl extends RestServiceBase<FosterRecord> implements FosterRecordRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FosterRecordRestServiceImpl.class);
    @org.springframework.beans.factory.annotation.Autowired
    private FosterRecordService fosterRecordService;

    public FosterRecordService getFosterRecordService() {
        return fosterRecordService;
    }

    public void setFosterRecordService(FosterRecordService fosterRecordService) {
        this.fosterRecordService = fosterRecordService;
    }

    @Override
    public CURDService<FosterRecord> getService() {
        return fosterRecordService;
    }

    @Override
    public RestResult<FosterRecord> over(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") String uuid) {
        return RestResult.OK(fosterRecordService.over(uuid));
    }
}
