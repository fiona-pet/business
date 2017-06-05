package com.fionapet.business.event;

import com.fionapet.business.entity.*;
import com.fionapet.business.service.FosterRecordDetailService;
import com.fionapet.business.service.FosterRecordService;
import com.fionapet.business.service.ItemCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by tom on 2017/6/4.
 */
@Component
public class PayNotifier implements ApplicationListener<PayEvent> {
    @Autowired
    private FosterRecordService fosterRecordService;

    @Autowired
    private FosterRecordDetailService fosterRecordDetailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PayNotifier.class);
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onApplicationEvent(PayEvent payEvent) {
        SettleAccountsView settleAccountsView = (SettleAccountsView) payEvent.getSource();
        LOGGER.info("onApplicationEvent, some thing I receive:{}, type:{}", payEvent.getId(),payEvent.getType());
        //减少 存款
        if ("寄养消费".equals(payEvent.getType())) {
            FosterRecordDetail fosterRecordDetail = fosterRecordDetailService.detail(settleAccountsView.getRelationDetailId());
            fosterRecordDetail.setPaidStatus("SM00051");
            fosterRecordDetail.setPaidTime(new Date());

            fosterRecordDetailService.createOrUpdte(fosterRecordDetail);

            FosterRecord fosterRecord = fosterRecordService.detail(fosterRecordDetail.getFosterId());
            fosterRecord.setInputMoney(fosterRecord.getInputMoney() - fosterRecordDetail.getTotalCost());
            fosterRecordService.createOrUpdte(fosterRecord);
        }

    }
}
