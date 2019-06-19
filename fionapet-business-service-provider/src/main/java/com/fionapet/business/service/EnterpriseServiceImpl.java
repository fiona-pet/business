package com.fionapet.business.service;

import com.fionapet.business.entity.Enterprise;
import com.fionapet.business.repository.EnterpriseDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业信息表 Created by tom on 2016-07-25 09:32:31.
 */

@Service
public class EnterpriseServiceImpl extends CURDServiceBase<Enterprise>
        implements EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Override
    public DaoBase<Enterprise> getDao() {
        return enterpriseDao;
    }

}
