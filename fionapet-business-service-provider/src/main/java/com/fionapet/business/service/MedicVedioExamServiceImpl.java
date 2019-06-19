package com.fionapet.business.service;

import com.fionapet.business.entity.MedicVedioExam;
import com.fionapet.business.repository.MedicVedioExamDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 医生影像样例 Created by tom on 2016-07-31 16:36:25.
 */

@Service
public class MedicVedioExamServiceImpl extends CURDServiceBase<MedicVedioExam>
        implements MedicVedioExamService {

    @Autowired
    private MedicVedioExamDao medicVedioExamDao;

    @Override
    public DaoBase<MedicVedioExam> getDao() {
        return medicVedioExamDao;
    }
}
