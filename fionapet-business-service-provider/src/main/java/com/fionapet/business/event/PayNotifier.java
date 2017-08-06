package com.fionapet.business.event;

import com.fionapet.business.entity.*;
import com.fionapet.business.event.pay.Payable;
import com.fionapet.business.service.*;
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
    Auth auth;

    @Autowired
    private FosterRecordService fosterRecordService;

    @Autowired
    private FosterRecordDetailService fosterRecordDetailService;

    @Autowired
    private StoreDirectSellDetailService storeDirectSellDetailService;

    @Autowired
    private ItemCountService itemCountService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PayNotifier.class);
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onApplicationEvent(PayEvent payEvent) {
        SettleAccountsView settleAccountsView = (SettleAccountsView) payEvent.getSource();
        LOGGER.info("onApplicationEvent, some thing I receive:{}, type:{}", payEvent.getId(),payEvent.getType());
        Payable pay = null;
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
        if ("直接销售".equals(payEvent.getType())){
            pay = new StoreDirectSellPay();
        }

        if (null != pay){
            pay.pay(settleAccountsView);
            pay.decrease(settleAccountsView);
        }

    }

    class StoreDirectSellPay implements Payable {
        public void pay(SettleAccountsView settleAccountsView){
            LOGGER.debug("StoreDirectSellPay.pay:{}", settleAccountsView);
        }

        @Override
        public void decrease(SettleAccountsView settleAccountsView) {
            LOGGER.debug("StoreDirectSellPay.decrease:{}", settleAccountsView);

            ItemCountChangeReason itemCountChangeReason = new ItemCountChangeReason();

            itemCountChangeReason.setChangeReason(settleAccountsView.getBusinessType());

            itemCountChangeReason.setItemCode(settleAccountsView.getItemCode());
            itemCountChangeReason.setItemName(settleAccountsView.getItemName());

            LOGGER.debug("auth:{}", auth.getCurrentUser());

            itemCountChangeReason.setCreateUserId(auth.getCurrentUser().getId());
            itemCountChangeReason.setUpdateUserId(auth.getCurrentUser().getId());
            itemCountChangeReason.setCreateDate(new Date());
            itemCountChangeReason.setUpdateDate(new Date());

            int itemNum = 0;
            try {
                itemNum = Integer.parseInt(settleAccountsView.getItemNum());
            }catch (NumberFormatException e){
                LOGGER.debug("NumberFormatException:{}", settleAccountsView.getItemNum());
            }

            itemCountService.decrease(itemNum, itemCountChangeReason);
        }
    }
}
