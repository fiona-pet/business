package com.fionapet.business.service;

import com.fionapet.business.entity.CheckWarehouseDetail;
import com.fionapet.business.repository.CheckWarehouseDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 检查仓库明细表 Created by tom on 2016-07-25 09:32:33.
 */

@Service
public class CheckWarehouseDetailServiceImpl extends CURDServiceBase<CheckWarehouseDetail>
        implements CheckWarehouseDetailService {

    @Autowired
    private CheckWarehouseDetailDao checkWarehouseDetailDao;

    @Override
    public DaoBase<CheckWarehouseDetail> getDao() {
        return checkWarehouseDetailDao;
    }
}
