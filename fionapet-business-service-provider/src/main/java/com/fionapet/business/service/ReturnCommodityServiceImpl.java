package com.fionapet.business.service;

import com.fionapet.business.entity.ReturnCommodity;
import com.fionapet.business.repository.ReturnCommodityDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 返回商品主表 Created by tom on 2016-07-18 11:56:09.
 */

@Service
public class ReturnCommodityServiceImpl extends CURDServiceBase<ReturnCommodity>
        implements ReturnCommodityService {

    @Autowired
    private ReturnCommodityDao returnCommodityDao;

    @Override
    public DaoBase<ReturnCommodity> getDao() {
        return returnCommodityDao;
    }
}
