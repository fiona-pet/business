package com.fionapet.business.service;

import com.fionapet.business.entity.*;
import com.fionapet.business.facade.vo.PayVO;
import com.fionapet.business.repository.DictTypeDetailDao;
import com.fionapet.business.repository.FosterRecordDetailDao;
import com.fionapet.business.repository.PrepayMoneyDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.FosterRecordDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  养育记录主表
* Created by tom on 2016-07-25 09:32:32.
 */
public class FosterRecordServiceImpl extends CURDServiceBase<FosterRecord> implements FosterRecordService {
    @Autowired
    private FosterRecordDao fosterRecordDao;
    @Autowired
    private FosterRecordDetailDao fosterRecordDetailDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;
    @Autowired
    private PrepayMoneyService prepayMoneyService;
    @Autowired
    private GestPaidRecordService gestPaidRecordService;

    @Override
    public DaoBase<FosterRecord> getDao() {
        return fosterRecordDao;
    }

    @Override
    @Transactional
    public FosterRecord over(String uuid) {
        FosterRecord fosterRecord = fosterRecordDao.findOne(uuid);
        if (null == fosterRecord){
            return null;
        }

        //预存记录
//        prepayMoneyService.setCurrentUser(this.getCurrentUser());
//        prepayMoneyService.setToken(this.getToken());
//        List<PrepayMoney> prepayMoneys = prepayMoneyService.findByRelationId(uuid);
//        Double payContent = 0.0d;
//
//        //支付记录
//        List<SettleAccountsView> settleAccountsViews = new ArrayList<SettleAccountsView>();
//
//        //寄养消费记录
//        List<FosterRecordDetail> fosterRecordDetails = fosterRecordDetailDao.findByFosterId(uuid);
//        for (FosterRecordDetail fosterRecordDetail: fosterRecordDetails){
//            PrepayMoney prepayMoney = new PrepayMoney();
//            prepayMoney.setGestId(fosterRecord.getGestId());
//            prepayMoney.setBusinessType("寄养");
//            prepayMoney.setOptionType("消费");
//            prepayMoney.setInputMoney(-fosterRecordDetail.getTotalCost());
//            prepayMoney.setRemark("出院结算消费");
//            prepayMoney.setRecordTime(new Date());
//            prepayMoneys.add(prepayMoney);
//
//            if (prepayMoney.getInputMoney() < 0){
//                payContent += prepayMoney.getInputMoney();
//            }
//
//            prepayMoneyService.createOrUpdte(prepayMoney);
//
//            SettleAccountsView settleAccountsView = new SettleAccountsView();
//            settleAccountsView.setGestId(fosterRecord.getGestId());
//            settleAccountsView.setBusinessType("寄养消费");
//            settleAccountsView.setRelationId(fosterRecord.getId());
//            settleAccountsView.setRelationDetailId(fosterRecordDetail.getId());
//            settleAccountsView.setItemCode(fosterRecordDetail.getItemCode());
//            settleAccountsView.setItemName(fosterRecordDetail.getItemName());
//            settleAccountsView.setItemNum(fosterRecordDetail.getItemNum()+"");
//            settleAccountsView.setItemCost(fosterRecordDetail.getSellPrice());
//            settleAccountsView.setItemUnit(fosterRecordDetail.getRecipeUnit());
//
//            settleAccountsViews.add(settleAccountsView);
//        }
//
//        //生成结算单
//        PayVO payVO = new PayVO();
//        GestPaidRecord gestPaidRecord = new GestPaidRecord();
//        gestPaidRecord.setOperateAction("寄养");
//        gestPaidRecord.setOperateContent(-payContent + "");
//        payVO.setGestPaidRecord(gestPaidRecord);
//        payVO.setSettleAccountsViews(settleAccountsViews);
//
//        gestPaidRecordService.pay(payVO);

        if( fosterRecord.getInputMoney() == 0) {
            //更新状态
            DictTypeDetail dictTypeDetail = dictTypeDetailDao.findByDictDetailCode("SM00039");

            fosterRecord.setStatus(dictTypeDetail);

            this.createOrUpdte(fosterRecord);
        }
        return fosterRecord;
    }
}
