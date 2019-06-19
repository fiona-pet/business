package com.fionapet.business.service;

import com.fionapet.business.entity.InHospitalPrescriptionDetail;
import com.fionapet.business.repository.InHospitalPrescriptionDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 住院处方明细 Created by tom on 2016-07-18 15:37:44.
 */

@Service
public class InHospitalPrescriptionDetailServiceImpl
        extends CURDServiceBase<InHospitalPrescriptionDetail>
        implements InHospitalPrescriptionDetailService {

    @Autowired
    private InHospitalPrescriptionDetailDao inHospitalPrescriptionDetailDao;

    @Override
    public DaoBase<InHospitalPrescriptionDetail> getDao() {
        return inHospitalPrescriptionDetailDao;
    }
}
