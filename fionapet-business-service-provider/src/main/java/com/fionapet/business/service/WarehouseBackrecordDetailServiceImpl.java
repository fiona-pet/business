package com.fionapet.business.service;

import com.fionapet.business.entity.WarehouseBackrecordDetail;
import com.fionapet.business.repository.WarehouseBackrecordDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 退回记录明细 Created by tom on 2016-07-18 11:56:09.
 */

@Service
public class WarehouseBackrecordDetailServiceImpl extends CURDServiceBase<WarehouseBackrecordDetail>
        implements WarehouseBackrecordDetailService {

    @Autowired
    private WarehouseBackrecordDetailDao warehouseBackrecordDetailDao;

    @Override
    public DaoBase<WarehouseBackrecordDetail> getDao() {
        return warehouseBackrecordDetailDao;
    }
}
