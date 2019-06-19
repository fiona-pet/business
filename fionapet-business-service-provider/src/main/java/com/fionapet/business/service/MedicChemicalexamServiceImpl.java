package com.fionapet.business.service;

import com.fionapet.business.entity.MedicChemicalexam;
import com.fionapet.business.repository.MedicChemicalexamDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 化验单明细 Created by tom on 2016-07-31 16:42:52.
 */

@Service
public class MedicChemicalexamServiceImpl extends CURDServiceBase<MedicChemicalexam>
        implements MedicChemicalexamService {

    @Autowired
    private MedicChemicalexamDao medicChemicalexamDao;

    @Override
    public DaoBase<MedicChemicalexam> getDao() {
        return medicChemicalexamDao;
    }
}
