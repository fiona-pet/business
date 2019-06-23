package com.fionapet.business.service;

import com.fionapet.business.entity.InHospitalHealth;
import com.fionapet.business.repository.InHospitalHealthDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 养育健康状况表 Created by tom on 2016-07-25 09:32:32.
 */

@Service
public class InHospitalHealthServiceImpl extends CURDServiceBase<InHospitalHealth>
        implements InHospitalHealthService {

    @Autowired
    private InHospitalHealthDao inHospitalHealthDao;

    @Override
    public DaoBase<InHospitalHealth> getDao() {
        return inHospitalHealthDao;
    }
}
