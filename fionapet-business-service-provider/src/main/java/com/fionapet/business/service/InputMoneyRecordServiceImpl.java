package com.fionapet.business.service;

import com.fionapet.business.entity.Gest;
import com.fionapet.business.entity.InputMoneyRecord;
import com.fionapet.business.repository.InputMoneyRecordDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 收款记录表 Created by tom on 2016-07-25 09:32:32.
 */

@Service
public class InputMoneyRecordServiceImpl extends CURDServiceBase<InputMoneyRecord>
        implements InputMoneyRecordService {

    @Autowired
    private InputMoneyRecordDao inputMoneyRecordDao;

    @Override
    public DaoBase<InputMoneyRecord> getDao() {
        return inputMoneyRecordDao;
    }

    @Override
    public void newRecord(Gest gest, Double money) {
        if (null == gest) {
            return;
        }

        InputMoneyRecord inputMoneyRecord = new InputMoneyRecord();

        inputMoneyRecord.setGestId(gest.getId());
        inputMoneyRecord.setGestName(gest.getGestName());
        inputMoneyRecord.setInputMoney(money);
        inputMoneyRecord.setInputMoneyDate(new Date());

        createOrUpdte(inputMoneyRecord);
    }
}
