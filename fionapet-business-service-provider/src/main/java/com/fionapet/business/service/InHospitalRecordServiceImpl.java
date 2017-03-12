package com.fionapet.business.service;

import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.InHospitalRecord;
import com.fionapet.business.repository.DictTypeDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.InHospitalRecordDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  医院记录表
* Created by tom on 2016-07-25 09:32:32.
 */
public class InHospitalRecordServiceImpl extends CURDServiceBase<InHospitalRecord> implements InHospitalRecordService {
    @Autowired
    private InHospitalRecordDao inHospitalRecordDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;

    @Override
    public DaoBase<InHospitalRecord> getDao() {
        return inHospitalRecordDao;
    }

    @Override
    public InHospitalRecord over(String inHospitalCode) {
        //出院 SM00039
        DictTypeDetail dictTypeDetail = dictTypeDetailDao.findByDictDetailCode("SM00039");

        InHospitalRecord inHospitalRecord = inHospitalRecordDao.findByInHospitalNo(inHospitalCode);
        inHospitalRecord.setStatus(dictTypeDetail);

        createOrUpdte(inHospitalRecord);

        return inHospitalRecord;
    }
}
