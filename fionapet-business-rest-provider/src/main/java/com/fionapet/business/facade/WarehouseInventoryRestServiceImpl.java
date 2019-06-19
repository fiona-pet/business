package com.fionapet.business.facade;

import com.fionapet.business.entity.WarehouseInventory;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.WarehouseInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 仓库存货清单
* Created by tom on 2016-07-18 11:56:09.
 */
public class WarehouseInventoryRestServiceImpl extends RestServiceBase<WarehouseInventory> implements WarehouseInventoryRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseInventoryRestServiceImpl.class);

    @Autowired
    private WarehouseInventoryService warehouseInventoryService;

    public WarehouseInventoryService getWarehouseInventoryService() {
        return warehouseInventoryService;
    }

    public void setWarehouseInventoryService(WarehouseInventoryService warehouseInventoryService) {
        this.warehouseInventoryService = warehouseInventoryService;
    }

    @Override
    public CURDService<WarehouseInventory> getService() {
        return warehouseInventoryService;
    }

}
