package com.fionapet.business.service;

import com.fionapet.business.entity.WarehouseOutrecordDetail;
import com.fionapet.business.repository.WarehouseOutrecordDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出库记录明细 Created by tom on 2016-07-18 11:56:11.
 */

@Service
public class WarehouseOutrecordDetailServiceImpl extends CURDServiceBase<WarehouseOutrecordDetail>
        implements WarehouseOutrecordDetailService {

    @Autowired
    private WarehouseOutrecordDetailDao warehouseOutrecordDetailDao;

    @Override
    public DaoBase<WarehouseOutrecordDetail> getDao() {
        return warehouseOutrecordDetailDao;
    }
}
