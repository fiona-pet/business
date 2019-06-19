package com.fionapet.business.service;

import com.fionapet.business.entity.MedicAppliance;
import com.fionapet.business.repository.MedicApplianceDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医生器具表 Created by tom on 2016-07-25 09:32:34.
 */

@Service
public class MedicApplianceServiceImpl extends CURDServiceBase<MedicAppliance>
        implements MedicApplianceService {

    @Autowired
    private MedicApplianceDao medicApplianceDao;

    @Override
    public DaoBase<MedicAppliance> getDao() {
        return medicApplianceDao;
    }
}
