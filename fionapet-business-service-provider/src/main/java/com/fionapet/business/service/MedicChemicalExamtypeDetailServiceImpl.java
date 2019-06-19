package com.fionapet.business.service;

import com.fionapet.business.entity.MedicChemicalExamtypeDetail;
import com.fionapet.business.repository.MedicChemicalExamtypeDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医生化验实例类型细节表 Created by tom on 2016-07-18 11:56:09.
 */

@Service
public class MedicChemicalExamtypeDetailServiceImpl
        extends CURDServiceBase<MedicChemicalExamtypeDetail>
        implements MedicChemicalExamtypeDetailService {

    @Autowired
    private MedicChemicalExamtypeDetailDao medicChemicalExamtypeDetailDao;

    @Override
    public DaoBase<MedicChemicalExamtypeDetail> getDao() {
        return medicChemicalExamtypeDetailDao;
    }
}
