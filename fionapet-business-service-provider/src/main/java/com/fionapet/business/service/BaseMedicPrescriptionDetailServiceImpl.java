package com.fionapet.business.service;

import com.fionapet.business.entity.BaseMedicPrescriptionDetail;
import com.fionapet.business.repository.BaseMedicPrescriptionDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处方明细 Created by tom on 2016-07-18 15:56:23.
 */

@Service
public class BaseMedicPrescriptionDetailServiceImpl
        extends CURDServiceBase<BaseMedicPrescriptionDetail>
        implements BaseMedicPrescriptionDetailService {

    @Autowired
    private BaseMedicPrescriptionDetailDao baseMedicPrescriptionDetailDao;

    @Override
    public DaoBase<BaseMedicPrescriptionDetail> getDao() {
        return baseMedicPrescriptionDetailDao;
    }
}
