package com.fionapet.business.facade;

import com.fionapet.business.entity.WarehouseOutrecord;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.WarehouseOutrecordService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;

/**
 * 出库记录
* Created by tom on 2016-07-18 11:56:10.
 */
public class WarehouseOutrecordRestServiceImpl extends RestServiceBase<WarehouseOutrecord> implements WarehouseOutrecordRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseOutrecordRestServiceImpl.class);

    private WarehouseOutrecordService warehouseOutrecordService;

    public WarehouseOutrecordService getWarehouseOutrecordService() {
        return warehouseOutrecordService;
    }

    public void setWarehouseOutrecordService(WarehouseOutrecordService warehouseOutrecordService) {
        this.warehouseOutrecordService = warehouseOutrecordService;
    }

    @Override
    public CURDService<WarehouseOutrecord> getService() {
        return warehouseOutrecordService;
    }

    @Override
    public RestResult<String> audit(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") String uuid) {
        return RestResult.OK(warehouseOutrecordService.audit(uuid)+"");
    }
}
