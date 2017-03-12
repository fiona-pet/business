package com.fionapet.business.service;

import com.fionapet.business.entity.InHospitalRecord;
import com.fionapet.business.entity.PrepayMoney;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.PrepayMoneyDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  提前付费表
* Created by tom on 2016-07-18 11:56:10.
 */
public class PrepayMoneyServiceImpl extends CURDServiceBase<PrepayMoney> implements PrepayMoneyService {
    @Autowired
    private PrepayMoneyDao prepayMoneyDao;
    @Autowired
    private InHospitalRecordService inHospitalRecordService;

    @Override
    public DaoBase<PrepayMoney> getDao() {
        return prepayMoneyDao;
    }

    @Override
    public PrepayMoney createOrUpdte(PrepayMoney entity) {
        String relationId = entity.getRelationId();

        InHospitalRecord inHospitalRecord = inHospitalRecordService.detail(relationId);

        if (null != inHospitalRecord){
            inHospitalRecord.setInputMoney(inHospitalRecord.getInputMoney() + entity.getInputMoney());
            inHospitalRecordService.createOrUpdte(inHospitalRecord);
        }

        return super.createOrUpdte(entity);
    }
}
