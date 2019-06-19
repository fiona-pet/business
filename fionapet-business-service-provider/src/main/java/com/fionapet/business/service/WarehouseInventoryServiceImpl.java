package com.fionapet.business.service;

import com.fionapet.business.entity.WarehouseInventory;
import com.fionapet.business.repository.WarehouseInventoryDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仓库存货清单 Created by tom on 2016-07-18 11:56:08.
 */

@Service
public class WarehouseInventoryServiceImpl extends CURDServiceBase<WarehouseInventory>
        implements WarehouseInventoryService {

    @Autowired
    private WarehouseInventoryDao warehouseInventoryDao;

    @Override
    public DaoBase<WarehouseInventory> getDao() {
        return warehouseInventoryDao;
    }
}
