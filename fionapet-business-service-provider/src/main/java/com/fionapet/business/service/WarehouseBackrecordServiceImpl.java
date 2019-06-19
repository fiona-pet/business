package com.fionapet.business.service;

import com.fionapet.business.entity.WarehouseBackrecord;
import com.fionapet.business.repository.WarehouseBackrecordDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退回记录 Created by tom on 2016-07-18 11:56:09.
 */

@Service
public class WarehouseBackrecordServiceImpl extends CURDServiceBase<WarehouseBackrecord>
        implements WarehouseBackrecordService {

    @Autowired
    private WarehouseBackrecordDao warehouseBackrecordDao;

    @Override
    public DaoBase<WarehouseBackrecord> getDao() {
        return warehouseBackrecordDao;
    }
}
