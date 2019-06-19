package com.fionapet.business.service;

import com.fionapet.business.entity.BillVO;
import com.fionapet.business.entity.CMSEntity;
import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.FinanceSettleAccounts;
import com.fionapet.business.entity.FinanceSettleAccountsDetail;
import com.fionapet.business.entity.Gest;
import com.fionapet.business.entity.GestPaidRecord;
import com.fionapet.business.entity.InHospitalPrescription;
import com.fionapet.business.entity.InHospitalPrescriptionDetail;
import com.fionapet.business.entity.InHospitalRecord;
import com.fionapet.business.entity.ItemType;
import com.fionapet.business.entity.MedicPrescriptionDetail;
import com.fionapet.business.entity.MedicRegisterRecord;
import com.fionapet.business.entity.Service;
import com.fionapet.business.entity.ServiceDetail;
import com.fionapet.business.entity.SettleAccountsView;
import com.fionapet.business.event.PayEvent;
import com.fionapet.business.exception.ApiException;
import com.fionapet.business.facade.vo.PayVO;
import com.fionapet.business.repository.BillVODao;
import com.fionapet.business.repository.DictTypeDetailDao;
import com.fionapet.business.repository.GestDao;
import com.fionapet.business.repository.GestPaidRecordDao;
import com.fionapet.business.repository.ItemTypeDao;
import com.fionapet.business.repository.PetDao;
import com.fionapet.business.repository.SettleAccountsDao;
import org.dubbo.x.repository.DaoBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

/**
 * 顾客支付记录表 Created by tom on 2016-07-25 09:32:34.
 */

@org.springframework.stereotype.Service
public class GestPaidRecordServiceImpl extends CURDEServiceBase<GestPaidRecord>
        implements GestPaidRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestPaidRecordServiceImpl.class);

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
    private MedicPrescriptionDetailService medicPrescriptionDetailService;

    @Autowired
    private InHospitalPrescriptionDetailService inHospitalPrescriptionDetailService;
    @Autowired
    private InHospitalPrescriptionService inHospitalPrescriptionService;
    @Autowired
    private InHospitalRecordService inHospitalRecordService;

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ServiceDetailService serviceDetailService;
    @Autowired
    private InputMoneyRecordService inputMoneyRecordService;


    @Autowired
    private ItemCountService itemCountService;
    @Autowired
    private ItemTypeDao itemTypeDao;

    @Override
    public DaoBase<GestPaidRecord> getDao() {
        return gestPaidRecordDao;
    }

    @Override
    public List<BillVO> billList(String timestamp) {

        List<BillVO> result = billVODao.findAllBy();
        LOGGER.debug("{} -> bill List:{}", timestamp, result);
        return result;
    }

    @Override
    public List<SettleAccountsView> billDetail(String gestId) {
        List<SettleAccountsView> result = settleAccountsDao.findByGestId(gestId);

        return result;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    @Transactional
    public FinanceSettleAccounts pay(PayVO pay) throws ApiException {
        FinanceSettleAccounts financeSettleAccounts = new FinanceSettleAccounts();
        try {
            if (null == pay) {
                return financeSettleAccounts;
            }

            List<SettleAccountsView> payList = pay.getSettleAccountsViews();

            if (payList.size() == 0) {
                return financeSettleAccounts;
            }

            Double total = 0D;
            Double dis = 1d;//折扣

            for (SettleAccountsView settleAccountsView : payList) {
                total +=
                        (settleAccountsView.getItemCost() * Double
                                .parseDouble(settleAccountsView.getItemNum()));
                try {
                    dis = Double.parseDouble(settleAccountsView.getIsVipDiscount());
                } catch (Exception e) {

                }
            }

            //已支付 状态
            DictTypeDetail dictTypeDetail = dictTypeDetailDao.findByDictDetailCode("SM00051");

            //会员信息
            Gest gest = gestDao.findOne(payList.get(0).getGestId() + "");

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

            financeSettleAccounts
                    .setShouldPaidMoney(total - financeSettleAccounts.getDisCountMoney());

            //付款金额
            financeSettleAccounts.setFactPaidMoney(
                    Double.parseDouble(pay.getGestPaidRecord().getOperateContent()));
            //找零
            financeSettleAccounts.setChangeMoney(
                    financeSettleAccounts.getFactPaidMoney() - financeSettleAccounts
                            .getShouldPaidMoney());
            appConfigService.setCurrentUser(getCurrentUser());
            financeSettleAccounts.setSettleCode(appConfigService.genNumberByName("结算单号"));

            if ("会员".equals(pay.getGestPaidRecord().getOperateAction())) {
                gest.setLastPaidTime(new Date());
                if (gest.getPrepayMoney() - financeSettleAccounts.getShouldPaidMoney() >= 0) {
                    gest.setPrepayMoney(
                            gest.getPrepayMoney() - financeSettleAccounts.getShouldPaidMoney());
                    gestDao.save(gest);
                    financeSettleAccounts.setChangeMoney(gest.getPrepayMoney());

                    inputMoneyRecordService.setCurrentUser(getCurrentUser());
                    inputMoneyRecordService.setToken(getToken());
                    inputMoneyRecordService
                            .newRecord(gest, -financeSettleAccounts.getShouldPaidMoney());
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
                FinanceSettleAccountsDetail
                        financeSettleAccountsDetail =
                        new FinanceSettleAccountsDetail();
                ItemType itemType = itemTypeDao.findByItemCode(settleAccountsView.getItemCode());

                financeSettleAccountsDetail.setStatus(CMSEntity.DEFAULT());
                financeSettleAccountsDetail.setSettleAccountsDetailId(financeSettleAccountsId);
                try {
                    financeSettleAccountsDetail.setBusiTypeId(settleAccountsView.getBusiTypeId());
                } catch (Exception e) {

                }
                financeSettleAccountsDetail.setItemCode(settleAccountsView.getItemCode());
                financeSettleAccountsDetail.setItemName(settleAccountsView.getItemName());
                financeSettleAccountsDetail.setPaidStatus(dictTypeDetail.getDictDetailCode());
                financeSettleAccountsDetail.setPackageUnit(settleAccountsView.getItemUnit());
                financeSettleAccountsDetail.setTotalCost(settleAccountsView.getItemCost());
                financeSettleAccountsDetail
                        .setTotalNum(Double.parseDouble(settleAccountsView.getItemNum()));
                financeSettleAccountsDetail
                        .setRelationDetailId(settleAccountsView.getRelationDetailId());
                financeSettleAccountsDetail.setRelationId(settleAccountsView.getRelationId());
                if (null != itemType) {
                    financeSettleAccountsDetail.setSellPrice(itemType.getRecipePrice());
                    financeSettleAccountsDetail.setBusiTypeId(itemType.getBusiTypeId());
                    financeSettleAccountsDetail.setInfactPrice(itemType.getRecipePrice() * dis);
                    financeSettleAccountsDetail.setDiscountMoney(
                            financeSettleAccountsDetail.getSellPrice() - financeSettleAccountsDetail
                                    .getInfactPrice());

                    financeSettleAccountsDetail.setSumOriginalMoney(
                            financeSettleAccountsDetail.getSellPrice() * financeSettleAccountsDetail
                                    .getTotalNum());
                    financeSettleAccountsDetail.setTotalCost(
                            financeSettleAccountsDetail.getInfactPrice()
                            * financeSettleAccountsDetail.getTotalNum());

                    financeSettleAccountsDetail.setSumDiscountMoney(
                            financeSettleAccountsDetail.getSumOriginalMoney()
                            - financeSettleAccountsDetail.getTotalCost());
                }

                if ("挂号费用".equals(settleAccountsView.getBusinessType())) {
                    MedicRegisterRecord
                            medicRegisterRecord =
                            medicRegisterRecordService.detail(settleAccountsView.getRelationId());
                    medicRegisterRecord.setPaidStatus(dictTypeDetail.getDictDetailCode());
                    medicRegisterRecord.setPaidTime(new Date());

                    financeSettleAccountsDetail
                            .setSellPrice(medicRegisterRecord.getRegisterPrice());
                    financeSettleAccountsDetail
                            .setInfactPrice(medicRegisterRecord.getRegisterPrice() * dis);
                    financeSettleAccountsDetail.setDiscountMoney(
                            financeSettleAccountsDetail.getSellPrice() - financeSettleAccountsDetail
                                    .getInfactPrice());
                    financeSettleAccountsDetail
                            .setSumOriginalMoney(financeSettleAccountsDetail.getSellPrice() * 1);
                    financeSettleAccountsDetail
                            .setTotalCost(financeSettleAccountsDetail.getInfactPrice() * 1);
                    financeSettleAccountsDetail.setSumDiscountMoney(
                            financeSettleAccountsDetail.getSellPrice() - financeSettleAccountsDetail
                                    .getInfactPrice());

                    medicRegisterRecordService.createOrUpdte(medicRegisterRecord);
                }

                financeSettleAccountsDetailService.createOrUpdte(financeSettleAccountsDetail);

                if ("处置处方".equals(settleAccountsView.getBusinessType())) {
                    MedicPrescriptionDetail
                            medicPrescriptionDetail =
                            medicPrescriptionDetailService
                                    .detail(settleAccountsView.getRelationDetailId());
                    medicPrescriptionDetail.setPaidStatus(dictTypeDetail.getDictDetailCode());
                    medicPrescriptionDetail.setPaidTime(new Date());

                    //消除库存
                    itemCountService.decrease(medicPrescriptionDetail);

                    medicPrescriptionDetailService.createOrUpdte(medicPrescriptionDetail);
                }

                if ("美容服务".equals(settleAccountsView.getBusinessType())) {
                    ServiceDetail
                            serviceDetail =
                            serviceDetailService.detail(settleAccountsView.getRelationDetailId());
                    serviceDetail.setPaidStatus(dictTypeDetail.getDictDetailCode());
                    serviceDetail.setPaidTime(new Date());

                    serviceDetailService.createOrUpdte(serviceDetail);

                    Service service = serviceService.detail(settleAccountsView.getRelationId());
                    if (null != service) {
                        service.setPaidStatus(dictTypeDetail.getDictDetailCode());
                        service.setPaidTime(new Date());

                        serviceService.createOrUpdte(service);
                    }
                }

                //减少 存款
                if ("住院".equals(pay.getGestPaidRecord().getOperateAction())) {
                    if ("住院处置处方".equals(settleAccountsView.getBusinessType())) {
                        InHospitalPrescriptionDetail
                                inHospitalPrescriptionDetail =
                                inHospitalPrescriptionDetailService
                                        .detail(settleAccountsView.getRelationDetailId());
                        inHospitalPrescriptionDetail
                                .setPaidStatus(dictTypeDetail.getDictDetailCode());
                        inHospitalPrescriptionDetail.setPaidTime(new Date());

                        //消除库存
                        itemCountService.decrease(inHospitalPrescriptionDetail);

                        inHospitalPrescriptionDetailService
                                .createOrUpdte(inHospitalPrescriptionDetail);
                    }
                    InHospitalPrescription
                            inHospitalPrescription =
                            inHospitalPrescriptionService
                                    .detail(settleAccountsView.getRelationId());
                    if (null != inHospitalPrescription) {
                        InHospitalRecord
                                inHospitalRecord =
                                inHospitalRecordService
                                        .detail(inHospitalPrescription.getInHospitalId());
                        if (null != inHospitalRecord) {
                            inHospitalRecord.setInputMoney(inHospitalRecord.getInputMoney() - Double
                                    .parseDouble(String.format("%.2f", financeSettleAccountsDetail
                                            .getTotalCost())));
                            inHospitalRecordService.createOrUpdte(inHospitalRecord);
                        }
                    }
                }

                this.publishEvent(
                        new PayEvent(settleAccountsView, pay.getGestPaidRecord().getOperateAction(),
                                     settleAccountsView.getBusinessType(), getToken()));

            }

            GestPaidRecord gestPaidRecord = new GestPaidRecord();
            gestPaidRecord.setGestId(gest.getId());
            gestPaidRecord.setGestName(gest.getGestName());
            gestPaidRecord.setOperateAction(pay.getGestPaidRecord().getOperateAction());
            gestPaidRecord.setSettleAccountsId(financeSettleAccountsId);
            gestPaidRecord.setOperateContent(pay.getGestPaidRecord().getOperateContent());

            createOrUpdte(gestPaidRecord);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(110, "pay error!");
        }

        return financeSettleAccounts;
    }

    @Override
    public GestPaidRecord getPaidType(String id) {
        GestPaidRecord gestPaidRecord = gestPaidRecordDao.findBySettleAccountsId(id);
        return gestPaidRecord;
    }
}
