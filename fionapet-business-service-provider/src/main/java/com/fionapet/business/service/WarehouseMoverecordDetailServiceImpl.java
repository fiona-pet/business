package com.fionapet.business.service;

import com.fionapet.business.entity.WarehouseMoverecordDetail;
import com.fionapet.business.repository.WarehouseMoverecordDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 移库记录明细 Created by tom on 2016-07-18 11:56:11.
 */

@Service
public class WarehouseMoverecordDetailServiceImpl extends CURDServiceBase<WarehouseMoverecordDetail>
        implements WarehouseMoverecordDetailService {

    @Autowired
    private WarehouseMoverecordDetailDao warehouseMoverecordDetailDao;

    @Override
    public DaoBase<WarehouseMoverecordDetail> getDao() {
        return warehouseMoverecordDetailDao;
    }
}
