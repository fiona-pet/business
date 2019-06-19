package com.fionapet.business.service;

import com.fionapet.business.entity.PrescriptionTemplateDetail;
import com.fionapet.business.repository.PrescriptionTemplateDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处方模版明细 Created by tom on 2016-07-31 16:42:51.
 */

@Service
public class PrescriptionTemplateDetailServiceImpl
        extends CURDServiceBase<PrescriptionTemplateDetail>
        implements PrescriptionTemplateDetailService {

    @Autowired
    private PrescriptionTemplateDetailDao prescriptionTemplateDetailDao;

    @Override
    public DaoBase<PrescriptionTemplateDetail> getDao() {
        return prescriptionTemplateDetailDao;
    }
}
