package com.fionapet.business.repository;

import com.fionapet.business.entity.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.*;

/**
 * 医生处理记录
* Created by tom on 2016-07-18 11:09:24.
 */
@ContextConfiguration(locations = {"/applicationContext-service.xml"})
public class MedicMedictreatRecordDaoTest extends SpringTransactionalTestCase {
    Logger logger = LoggerFactory.getLogger(MedicMedictreatRecordDaoTest.class);

    @Autowired
    private MedicMedictreatRecordDao medicMedictreatRecordDao;

    @Autowired
    private GestPaidRecordDao gestPaidRecordDao;

    @Autowired
    private MedicRegisterRecordDao medicRegisterRecordDao;

    @Autowired
    private  MedicPrescriptionDao medicPrescriptionDao;

    @Autowired
    private MedicPrescriptionDetailDao medicPrescriptionDetailDao;

    @Autowired
    private FinanceSettleAccountsDao financeSettleAccountsDao;

    @Autowired
    private FinanceSettleAccountsDetailDao financeSettleAccountsDetailDao;

    @Test
    public void findAll(){
        List<MedicMedictreatRecord> medicMedictreatRecords = medicMedictreatRecordDao.findAllBy();

        Assert.assertNotNull(medicMedictreatRecords);

        Assert.assertEquals(medicMedictreatRecords.size(), 0);
    }

    @Test
    @Rollback(false)
    public void lfcreate4()throws ParseException{
        //2 173275
        //3 155966 old
        Date start = DateUtils.parseDate("2018-04-01", "yyyy-MM-dd");
        Date end = DateUtils.parseDate("2018-05-01", "yyyy-MM-dd");
        createMonth(start, end, 34000);
    }

    @Test
    @Rollback(false)
    public void create2()throws ParseException{
        //2 173275
        //3 155966 old
        Date start = DateUtils.parseDate("2018-02-01", "yyyy-MM-dd");
        Date end = DateUtils.parseDate("2018-03-01", "yyyy-MM-dd");
        createMonth(start, end, 192000);
    }

    @Test
    @Rollback(false)
    public void create3()throws ParseException{
        //2 173275
        //3 155966 old
        Date start = DateUtils.parseDate("2018-03-01", "yyyy-MM-dd");
        Date end = DateUtils.parseDate("2018-04-01", "yyyy-MM-dd");
        createMonth(start, end, 194000);
    }

    @Test
    @Rollback(false)
    public void create4()throws ParseException{
        Date start = DateUtils.parseDate("2018-04-01", "yyyy-MM-dd");
        Date end = DateUtils.parseDate("2018-05-01", "yyyy-MM-dd");
        createMonth(start, end, 196000);
    }

    @Test
    @Rollback(false)
    public void create6()throws ParseException{
        Date start = DateUtils.parseDate("2018-06-01", "yyyy-MM-dd");
        Date end = DateUtils.parseDate("2018-07-01", "yyyy-MM-dd");
        createMonth(start, end, 212000);
    }

    public void createMonth(Date start, Date end, int target) throws ParseException {


        List<MedicMedictreatRecord> medicMedictreatRecords = findMonth3(DateUtils.addMonths(start, -1), DateUtils.addMonths(end, -1), ImmutableList.of("去势", "绝育", "免", "火化", "细小", "猫瘟"), ImmutableList.of("去势","绝育","去势","寄养","疫","新购","老年"));

        for (MedicMedictreatRecord medicMedictreatRecord: medicMedictreatRecords){

            if (sum(start,end) > target){
                return;
            }

            try {
                // 复制注册信息
                MedicRegisterRecord registerRecord = createRegisterRecord(medicMedictreatRecord.getRegisterNo());

                if(null == registerRecord){
                    continue;
                }

                // 复制诊疗信息
                MedicMedictreatRecord medicMedictreatRecordNew = createMedicMedictreatRecord(registerRecord, medicMedictreatRecord);

                // 复制处方信息
                // 复制结算信息
                // 复制结算详情
                // 复制支付记录
                List<MedicPrescription> medicPrescriptionsNew = createMedicPrescription(medicMedictreatRecord.getRegisterNo(), medicMedictreatRecordNew, medicMedictreatRecord);
            }catch (Exception e){
                logger.error("copy error!", e);
            }

        }
    }

    private double sum(Date start, Date end){
        List<String[]> gestPaidRecords = gestPaidRecordDao.getReportForOperateAction(start, end);
        double sum = 0;
        for (Object[] pr: gestPaidRecords){
            sum += Double.parseDouble(pr[0]+"");
        }
        logger.debug("{}, sum:{}", start +"->"+ end, sum);
        return sum;
    }


    private List<FinanceSettleAccounts> getFinanceSettleAccounts(List<FinanceSettleAccountsDetail> financeSettleAccountsDetailsOld) {
        Set<String> ids = new HashSet<String>();
        for (FinanceSettleAccountsDetail financeSettleAccountsDetail: financeSettleAccountsDetailsOld){
            ids.add(financeSettleAccountsDetail.getSettleAccountsDetailId());
        }

        List<FinanceSettleAccounts> financeSettleAccountsOld = financeSettleAccountsDao.findByIdIn(ids);

        return financeSettleAccountsOld;
    }

    @Test
    public void testFindByRelationIdIn(){
        List<FinanceSettleAccountsDetail> financeSettleAccountsDetailsOld = financeSettleAccountsDetailDao.findByRelationId("ff80808160e7fa9301614f3cdb7d1f6d");
        logger.debug("financeSettleAccountsDetailsOld.size:{}" , financeSettleAccountsDetailsOld.size());
    }

    private List<MedicPrescription> createMedicPrescription(String registerNo, MedicMedictreatRecord medicMedictreatRecordNew, MedicMedictreatRecord medicMedictreatRecordOld) {
        // 查询处方信息
        List<MedicPrescription> medicPrescriptionsOld = medicPrescriptionDao.findByMedicRecordId(medicMedictreatRecordOld.getId());

        List<MedicPrescription> medicPrescriptionsNew = new ArrayList<MedicPrescription>();

       for (MedicPrescription medicPrescription: medicPrescriptionsOld){
           MedicPrescription medicPrescriptionNew = copy(medicPrescription, medicMedictreatRecordNew);
           medicPrescriptionsNew.add(medicPrescriptionNew);
       }

       logger.debug("medicPrescriptionsNew:{}",medicPrescriptionsNew);

       return medicPrescriptionsNew;

    }

    public MedicPrescription copy(MedicPrescription medicPrescriptionOld, MedicMedictreatRecord medicMedictreatRecordNew){
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);

        if (null == medicMedictreatRecordNew){
            logger.warn("就诊编号为空!");
        }

        MedicPrescription medicPrescriptionOrgi = medicPrescriptionOld;
        MedicPrescription medicPrescription = new MedicPrescription();
        if (null != medicPrescriptionOrgi){
            //属性复制
            try {
                BeanUtilsBean.getInstance().copyProperties(medicPrescription, medicPrescriptionOrgi);
            } catch (Exception e) {
                logger.warn("复制处方数据出错!", e);
            }
        }

        medicPrescription.setId(null);
        medicPrescription.setMedicRecordId(medicMedictreatRecordNew.getId());
        medicPrescription.setMedicRecordCode(medicMedictreatRecordNew.getMediTreatmentCode());
        medicPrescription.setCreateDate(medicMedictreatRecordNew.getCreateDate());
        medicPrescription.setUpdateDate(medicMedictreatRecordNew.getCreateDate());

        //获取 病例编号
        String endStr = getAddEndStr(medicMedictreatRecordNew.getCreateDate().getDay());
        medicPrescription.setPrescriptionCode(medicPrescriptionOld.getPrescriptionCode() + endStr);

        medicPrescriptionDao.save(medicPrescription);

        //处方明细 复制
        List<MedicPrescriptionDetail> medicPrescriptionDetails = medicPrescriptionDetailDao.findByPrescriptionId(medicPrescriptionOrgi.getId());

        for (MedicPrescriptionDetail medicPrescriptionDetail: medicPrescriptionDetails){
            MedicPrescriptionDetail medicPrescriptionDetailDest = new MedicPrescriptionDetail();

            //属性复制
            try {
                BeanUtilsBean.getInstance().copyProperties(medicPrescriptionDetailDest, medicPrescriptionDetail);

            } catch (Exception e) {
                logger.debug("复制处方明细数据出错!");
            }

            medicPrescriptionDetailDest.setId(null);
            medicPrescriptionDetailDest.setPrescriptionId(medicPrescription.getId());
            medicPrescriptionDetailDest.setCreateDate(medicPrescription.getCreateDate());
            medicPrescriptionDetailDest.setUpdateDate(medicPrescription.getUpdateDate());
            medicPrescriptionDetailDest.setPaidTime(medicPrescription.getUpdateDate());

            medicPrescriptionDetailDao.save(medicPrescriptionDetailDest);

            coypFinanceSettle(medicPrescriptionOld.getId(), medicPrescriptionDetail.getId(), medicPrescription.getId(), medicPrescriptionDetailDest.getId(), medicPrescription.getUpdateDate());
        }



        return medicPrescription;
    }

    private void coypFinanceSettle(String medicPrescriptionIdOld, String medicPrescriptionDetailIdOld, String medicPrescriptionId, String medicPrescriptionDetailId, Date date) {
        logger.debug("coypFinanceSettle : medicPrescriptionIdOld:{} , medicPrescriptionDetailIdOld:{}", medicPrescriptionIdOld, medicPrescriptionDetailIdOld);
        List<FinanceSettleAccountsDetail> financeSettleAccountsDetail = financeSettleAccountsDetailDao.findByRelationId(medicPrescriptionId);
        FinanceSettleAccounts financeSettleAccounts = null;
        // 判断 结算单是否存在
        if (null == financeSettleAccountsDetail || financeSettleAccountsDetail.size() == 0){
            // 复制结算单
            FinanceSettleAccountsDetail financeSettleAccountsDetailOld = financeSettleAccountsDetailDao.findByRelationId(medicPrescriptionIdOld).get(0);
            FinanceSettleAccounts financeSettleAccountsOld = financeSettleAccountsDao.findOne(financeSettleAccountsDetailOld.getSettleAccountsDetailId());

            FinanceSettleAccounts financeSettleAccountsNew = new FinanceSettleAccounts();
            BeanUtils.copyProperties(financeSettleAccountsOld,financeSettleAccountsNew,"id");

            financeSettleAccountsNew.setSettleCode(financeSettleAccountsOld.getSettleCode() + getAddEndStr(date.getDay()));
            financeSettleAccountsNew.setCreateDate(date);
            financeSettleAccountsNew.setUpdateDate(date);

            financeSettleAccountsDao.save(financeSettleAccountsNew);

            GestPaidRecord gestPaidRecordOld = gestPaidRecordDao.findBySettleAccountsId(financeSettleAccountsOld.getId());
            GestPaidRecord gestPaidRecordNew = new GestPaidRecord();

            BeanUtils.copyProperties(gestPaidRecordOld,gestPaidRecordNew,"id");
            gestPaidRecordNew.setCreateDate(date);
            gestPaidRecordNew.setUpdateDate(date);
            gestPaidRecordNew.setSettleAccountsId(financeSettleAccountsNew.getId());

            gestPaidRecordDao.save(gestPaidRecordNew);

            financeSettleAccounts = financeSettleAccountsNew;
        }else{
            financeSettleAccounts = financeSettleAccountsDao.findOne(financeSettleAccountsDetail.get(0).getSettleAccountsDetailId());
        }

        // 复制明细
        FinanceSettleAccountsDetail financeSettleAccountsDetailOld = financeSettleAccountsDetailDao.findOneByRelationIdAndRelationDetailId(medicPrescriptionIdOld, medicPrescriptionDetailIdOld);

        if (null == financeSettleAccountsDetailOld) return;

        FinanceSettleAccountsDetail financeSettleAccountsDetailNew = new FinanceSettleAccountsDetail();
        BeanUtils.copyProperties(financeSettleAccountsDetailOld,financeSettleAccountsDetailNew,"id");
        financeSettleAccountsDetailNew.setCreateDate(date);
        financeSettleAccountsDetailNew.setUpdateDate(date);
        financeSettleAccountsDetailNew.setSettleAccountsDetailId(financeSettleAccounts.getId());
        financeSettleAccountsDetailNew.setRelationId(medicPrescriptionId);
        financeSettleAccountsDetailNew.setRelationDetailId(medicPrescriptionDetailId);

        financeSettleAccountsDetailDao.save(financeSettleAccountsDetailNew);
    }

    private MedicMedictreatRecord createMedicMedictreatRecord(MedicRegisterRecord registerRecord, MedicMedictreatRecord medicMedictreatRecord) {
        MedicMedictreatRecord medicMedictreatRecordNew = new MedicMedictreatRecord();

        BeanUtils.copyProperties(medicMedictreatRecord,medicMedictreatRecordNew,"id");

        String endStr = StringUtils.leftPad(medicMedictreatRecord.getCreateDate().getDay()+"", 2, '0');

        medicMedictreatRecordNew.setRegisterNo(registerRecord.getRegisterNo());
        medicMedictreatRecordNew.setCreateDate(registerRecord.getCreateDate());
        medicMedictreatRecordNew.setUpdateDate(registerRecord.getUpdateDate());
        medicMedictreatRecordNew.setMediTreatmentCode(medicMedictreatRecordNew.getMediTreatmentCode() + endStr);

        medicMedictreatRecordDao.save(medicMedictreatRecordNew);

        return medicMedictreatRecordNew;
    }

    public MedicRegisterRecord createRegisterRecord(String registerRecord){
        MedicRegisterRecord medicRegisterRecord = medicRegisterRecordDao.findByRegisterNo(registerRecord);
        if (null == medicRegisterRecord){
            return null;
        }

        String endStr = getAddEndStr(medicRegisterRecord.getCreateDate().getDay());

        MedicRegisterRecord medicRegisterRecordNew = new MedicRegisterRecord();
        BeanUtils.copyProperties(medicRegisterRecord,medicRegisterRecordNew,"id");

        medicRegisterRecordNew.setRegisterNo(medicRegisterRecord.getRegisterNo() + endStr);
        medicRegisterRecordNew.setCreateDate(DateUtils.addMonths(medicRegisterRecord.getCreateDate(), 1));
        medicRegisterRecordNew.setUpdateDate(DateUtils.addMonths(medicRegisterRecord.getUpdateDate(), 1));

        //添加挂号信息
        medicRegisterRecordDao.save(medicRegisterRecordNew);

        return medicRegisterRecordNew;
    }

    public String getAddEndStr(int day){
        return StringUtils.leftPad(day + "", 2, '0');
    }


    public List<MedicMedictreatRecord> findMonth3(final Date start, final Date end, final List<String> diagnosed, final List<String> rheme){
        Specification<MedicMedictreatRecord> spec = new Specification<MedicMedictreatRecord>() {
            @Override
            public Predicate toPredicate(Root<MedicMedictreatRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                List<Predicate> predicates = Lists.newArrayList();

                Path expression = root.get("diagnosed");
                for (String str: diagnosed) {
                    predicates.add(builder.notLike(expression, "%" + str + "%"));
                }

                expression = root.get("rheme");
                for (String str: rheme) {
                    predicates.add(builder.notLike(expression, "%" + str + "%"));
                }

                expression = root.get("createDate");
                predicates.add(builder.between(expression, start , end));


                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<MedicMedictreatRecord> medicMedictreatRecords = medicMedictreatRecordDao.findAll(spec);

        logger.debug("{}, size:{}", start +"->"+ end, medicMedictreatRecords.size());

        return medicMedictreatRecords;

    }
}
