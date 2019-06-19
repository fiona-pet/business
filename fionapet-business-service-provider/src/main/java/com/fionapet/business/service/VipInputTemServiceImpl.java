package com.fionapet.business.service;

import com.fionapet.business.entity.VipInputTem;
import com.fionapet.business.repository.VipInputTemDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * VIP信息 Created by tom on 2016-07-18 11:56:11.
 */

@Service
public class VipInputTemServiceImpl extends CURDServiceBase<VipInputTem>
        implements VipInputTemService {

    @Autowired
    private VipInputTemDao vipInputTemDao;

    @Override
    public DaoBase<VipInputTem> getDao() {
        return vipInputTemDao;
    }
}
