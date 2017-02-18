package com.fionapet.business.service;

import com.fionapet.business.entity.*;
import com.fionapet.business.exception.ApiException;
import com.fionapet.business.facade.vo.PayVO;
import com.fionapet.business.repository.*;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *  顾客影像记录表
* Created by tom on 2016-07-25 09:32:34.
 */
public class GestPaidRecordServiceImpl extends CURDServiceBase<GestPaidRecord> implements GestPaidRecordService {
    @Autowired
    private GestPaidRecordDao gestPaidRecordDao;

    @Autowired
    private GestDao gestDao;

    @Autowired
    private PetDao petDao;

    @Autowired
    private BillVODao billVODao;
    @Autowired
    private SettleAccountsDao settleAccountsDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;

    @Autowired
    private FinanceSettleAccountsService financeSettleAccountsService;
    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private FinanceSettleAccountsDetailService financeSettleAccountsDetailService;
    @Autowired
    private MedicRegisterRecordService medicRegisterRecordService;

    @Autowired
    private  MedicPrescriptionDetailService medicPrescriptionDetailService;

    @Autowired
    private ItemCountService itemCountService;

    @Override
    public DaoBase<GestPaidRecord> getDao() {
        return gestPaidRecordDao;
    }

    @Override
    public List<BillVO> billList() {List<BillVO> result = billVODao.findAllBy();
        return result;
    }

    @Override
    public List<SettleAccountsView> billDetail(String gestId) {
        List<SettleAccountsView> result = settleAccountsDao.findByGestId(gestId);

        return result;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public FinanceSettleAccounts pay(PayVO pay) throws ApiException{
        FinanceSettleAccounts financeSettleAccounts = new FinanceSettleAccounts();
        try {
            if (null == pay)
                return financeSettleAccounts;

            List<SettleAccountsView> payList = pay.getSettleAccountsViews();

            if (payList.size() == 0) {
                return financeSettleAccounts;
            }

            Double total = 0D;
            Double dis = 1d;//折扣

            for (SettleAccountsView settleAccountsView : payList) {
                total += (settleAccountsView.getItemCost() * Double.parseDouble(settleAccountsView.getItemNum()));
                try {
                    dis = Double.parseDouble(settleAccountsView.getIsVipDiscount());
                } catch (Exception e) {

                }
            }

            //已支付 状态
            DictTypeDetail dictTypeDetail = dictTypeDetailDao.findByDictDetailCode("SM00051");

            //会员信息
            Gest gest = gestDao.findOne(payList.get(0).getGestId()+"");

            if (null == gest) {
                gest = new Gest();
                gest.setGestCode("vip000000");
                gest.setId(UUID.randomUUID() + "");
                gest.setGestName("散客");
            }

            //支付记录
            financeSettleAccountsService.setCurrentUser(getCurrentUser());

            financeSettleAccounts.setGestCode(gest.getGestCode());
            financeSettleAccounts.setGestId(gest.getId());
            financeSettleAccounts.setGestName(gest.getGestName());

            financeSettleAccounts.setStatus(dictTypeDetail);
            financeSettleAccounts.setTotalMoney(total);
            financeSettleAccounts.setDisCountMoney(total * (1 - dis));

            financeSettleAccounts.setShouldPaidMoney(total - financeSettleAccounts.getDisCountMoney());

            //付款金额
            financeSettleAccounts.setFactPaidMoney(Double.parseDouble(pay.getGestPaidRecord().getOperateContent()));
            //找零
            financeSettleAccounts.setChangeMoney(financeSettleAccounts.getFactPaidMoney() - financeSettleAccounts.getShouldPaidMoney());
            appConfigService.setCurrentUser(getCurrentUser());
            financeSettleAccounts.setSettleCode(appConfigService.genNumberByName("结算单号"));


            if ("会员".equals(pay.getGestPaidRecord().getOperateAction())) {
                gest.setLastPaidTime(new Date());
                if (gest.getPrepayMoney() - financeSettleAccounts.getShouldPaidMoney() >= 0) {
                    gest.setPrepayMoney(gest.getPrepayMoney() - financeSettleAccounts.getShouldPaidMoney());
                    gestDao.save(gest);
                    financeSettleAccounts.setChangeMoney(gest.getPrepayMoney());
                } else {
                    throw new ApiException(101, "余额不足");
                }
            }


            financeSettleAccountsService.createOrUpdte(financeSettleAccounts);

            String financeSettleAccountsId = financeSettleAccounts.getId();

            financeSettleAccountsDetailService.setCurrentUser(getCurrentUser());
            medicRegisterRecordService.setCurrentUser(getCurrentUser());
            medicPrescriptionDetailService.setCurrentUser(getCurrentUser());

            for (SettleAccountsView settleAccountsView : payList) {
                FinanceSettleAccountsDetail financeSettleAccountsDetail = new FinanceSettleAccountsDetail();

                financeSettleAccountsDetail.setStatus(CMSEntity.DEFAULT());
                financeSettleAccountsDetail.setSettleAccountsDetailId(financeSettleAccountsId);
                try {
                    financeSettleAccountsDetail.setBusiTypeId(Integer.parseInt(settleAccountsView.getBusiTypeId()));
                } catch (Exception e) {

                }
                financeSettleAccountsDetail.setItemCode(settleAccountsView.getItemCode());
                financeSettleAccountsDetail.setItemName(settleAccountsView.getItemName());
                financeSettleAccountsDetail.setPaidStatus(dictTypeDetail.getDictDetailCode());
                financeSettleAccountsDetail.setPackageUnit(settleAccountsView.getItemUnit());
                financeSettleAccountsDetail.setTotalCost(settleAccountsView.getItemCost());
                financeSettleAccountsDetail.setTotalNum(Double.parseDouble(settleAccountsView.getItemNum()));
                financeSettleAccountsDetail.setRelationDetailId(settleAccountsView.getRelationDetailId());
                financeSettleAccountsDetail.setRelationId(settleAccountsView.getRelationId());


                financeSettleAccountsDetailService.createOrUpdte(financeSettleAccountsDetail);


                if ("挂号费用".equals(settleAccountsView.getBusinessType())) {
                    MedicRegisterRecord medicRegisterRecord = medicRegisterRecordService.detail(settleAccountsView.getRelationId());
                    medicRegisterRecord.setPaidStatus(dictTypeDetail.getDictDetailCode());
                    medicRegisterRecord.setPaidTime(new Date());
                    medicRegisterRecordService.createOrUpdte(medicRegisterRecord);
                }

                if ("处置处方".equals(settleAccountsView.getBusinessType())) {
                    MedicPrescriptionDetail medicPrescriptionDetail = medicPrescriptionDetailService.detail(settleAccountsView.getRelationDetailId());
                    medicPrescriptionDetail.setPaidStatus(dictTypeDetail.getDictDetailCode());
                    medicPrescriptionDetail.setPaidTime(new Date());

                    //消除库存
                    itemCountService.decrease(medicPrescriptionDetail);

                    medicPrescriptionDetailService.createOrUpdte(medicPrescriptionDetail);

                }
            }

            GestPaidRecord gestPaidRecord = new GestPaidRecord();
            gestPaidRecord.setGestId(gest.getId());
            gestPaidRecord.setGestName(gest.getGestName());
            gestPaidRecord.setOperateAction(pay.getGestPaidRecord().getOperateAction());
            gestPaidRecord.setSettleAccountsId(financeSettleAccountsId);
            gestPaidRecord.setOperateContent(pay.getGestPaidRecord().getOperateContent());

            createOrUpdte(gestPaidRecord);
        }catch (Exception e){
            e.printStackTrace();
            throw new ApiException(110, "pay error!");
        }

        return financeSettleAccounts;
    }
}
