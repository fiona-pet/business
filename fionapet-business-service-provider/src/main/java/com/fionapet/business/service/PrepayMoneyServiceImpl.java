package com.fionapet.business.service;

import com.fionapet.business.entity.FosterRecord;
import com.fionapet.business.entity.InHospitalRecord;
import com.fionapet.business.entity.PrepayMoney;
import com.fionapet.business.repository.PrepayMoneyDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提前付费表 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class
PrepayMoneyServiceImpl extends CURDServiceBase<PrepayMoney> implements PrepayMoneyService {

    @Autowired
    private PrepayMoneyDao prepayMoneyDao;
    @Autowired
    private InHospitalRecordService inHospitalRecordService;
    @Autowired
    private FosterRecordService fosterRecordService;

    @Override
    public DaoBase<PrepayMoney> getDao() {
        return prepayMoneyDao;
    }

    @Override
    public PrepayMoney createOrUpdte(PrepayMoney entity) {
        String relationId = entity.getRelationId();

        InHospitalRecord inHospitalRecord = inHospitalRecordService.detail(relationId);

        if (null != inHospitalRecord) {
            inHospitalRecordService.setCurrentUser(getCurrentUser());
            inHospitalRecordService.setToken(getToken());
            inHospitalRecord
                    .setInputMoney(inHospitalRecord.getInputMoney() + entity.getInputMoney());
            inHospitalRecordService.createOrUpdte(inHospitalRecord);
        }

        FosterRecord fosterRecord = fosterRecordService.detail(relationId);
        if (null != fosterRecord) {
            fosterRecordService.setCurrentUser(getCurrentUser());
            fosterRecordService.setToken(getToken());
            fosterRecord.setInputMoney(fosterRecord.getInputMoney() + entity.getInputMoney());
            fosterRecordService.createOrUpdte(fosterRecord);
        }

        return super.createOrUpdte(entity);
    }

    @Override
    public List<PrepayMoney> findByRelationId(String uuid) {
        return this.prepayMoneyDao.findByRelationId(uuid);
    }
}
