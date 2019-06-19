package com.fionapet.business.service;

import com.fionapet.business.entity.BusinesCate;
import com.fionapet.business.repository.BusinesCateDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务类型表 Created by tom on 2016-07-31 16:36:25.
 */

@Service
public class BusinesCateServiceImpl extends CURDServiceBase<BusinesCate>
        implements BusinesCateService {

    @Autowired
    private BusinesCateDao businesCateDao;

    @Override
    public DaoBase<BusinesCate> getDao() {
        return businesCateDao;
    }
}
