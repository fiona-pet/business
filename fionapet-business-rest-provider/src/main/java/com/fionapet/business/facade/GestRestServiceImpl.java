package com.fionapet.business.facade;

import com.fionapet.business.entity.Gest;
import com.fionapet.business.facade.vo.RechargeVO;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.exception.ApiException;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.GestService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;

/**
 * 会员
* Created by tom on 2016-07-22 14:21:59.
 */
public class GestRestServiceImpl extends RestServiceBase<Gest> implements GestRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestRestServiceImpl.class);
    @org.springframework.beans.factory.annotation.Autowired
    private GestService gestService;

    public GestService getGestService() {
        return gestService;
    }

    public void setGestService(GestService gestService) {
        this.gestService = gestService;
    }

    @Override
    public CURDService<Gest> getService() {
        return gestService;
    }

    @Override
    public RestResult<RechargeVO> recharge(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") String uuid, Double money) throws ApiException {
        return RestResult.OK(gestService.recharge(uuid, money));
    }
}
