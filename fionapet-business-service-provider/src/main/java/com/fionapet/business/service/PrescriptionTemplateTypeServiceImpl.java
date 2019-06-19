package com.fionapet.business.service;

import com.fionapet.business.entity.PrescriptionTemplateType;
import com.fionapet.business.repository.PrescriptionTemplateTypeDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处方模版类型 Created by tom on 2016-07-31 16:42:52.
 */

@Service
public class PrescriptionTemplateTypeServiceImpl extends CURDServiceBase<PrescriptionTemplateType>
        implements PrescriptionTemplateTypeService {

    @Autowired
    private PrescriptionTemplateTypeDao prescriptionTemplateTypeDao;

    @Override
    public DaoBase<PrescriptionTemplateType> getDao() {
        return prescriptionTemplateTypeDao;
    }
}
