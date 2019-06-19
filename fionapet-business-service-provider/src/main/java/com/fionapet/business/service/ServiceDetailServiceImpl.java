package com.fionapet.business.service;

import com.fionapet.business.entity.ServiceDetail;
import com.fionapet.business.repository.ServiceDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务明细 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class ServiceDetailServiceImpl extends CURDServiceBase<ServiceDetail>
        implements ServiceDetailService {

    @Autowired
    private ServiceDetailDao serviceDetailDao;

    @Override
    public DaoBase<ServiceDetail> getDao() {
        return serviceDetailDao;
    }
}
