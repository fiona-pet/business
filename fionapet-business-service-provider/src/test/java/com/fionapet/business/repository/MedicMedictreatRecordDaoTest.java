package com.fionapet.business.repository;

import com.fionapet.business.entity.MedicMedictreatRecord;
import com.fionapet.business.entity.MedicRegisterRecord;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dubbo.x.entity.SearchFilter;
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
import java.util.Date;
import java.util.List;

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

    @Test
    public void findAll(){
        List<MedicMedictreatRecord> medicMedictreatRecords = medicMedictreatRecordDao.findAllBy();

        Assert.assertNotNull(medicMedictreatRecords);

        Assert.assertEquals(medicMedictreatRecords.size(), 0);
    }

    @Test
    @Rollback(false)
    public void create3() throws ParseException {
        //2 173275
        //3 155966 old
        Date start = DateUtils.parseDate("2018-03-01", "yyyy-MM-dd");
        Date end = DateUtils.parseDate("2018-04-01", "yyyy-MM-dd");

        List<String[]> gestPaidRecords = gestPaidRecordDao.getReportForOperateAction(start, end);
        double sum = 0;
        for (Object[] pr: gestPaidRecords){
            sum += Double.parseDouble(pr[0]+"");
        }
        logger.debug("{}, sum:{}", start +"->"+ end, sum);

        List<MedicMedictreatRecord> medicMedictreatRecords = findMonth3(DateUtils.addMonths(start, -1), DateUtils.addMonths(end, -1), ImmutableList.of("去势", "绝育", "免", "火化", "细小", "猫瘟"), ImmutableList.of("去势","绝育","去势","寄养","疫","新购","老年"));

        for (MedicMedictreatRecord medicMedictreatRecord: medicMedictreatRecords){
            // 复制注册信息
            MedicRegisterRecord registerRecord = createRegisterRecord(medicMedictreatRecord.getRegisterNo());

            if(null == registerRecord){
                continue;
            }

            // 复制诊疗信息
            MedicMedictreatRecord medicMedictreatRecordNew = createMedicMedictreatRecord(registerRecord, medicMedictreatRecord);

            // 复制处方信息
            createMedicPrescription(medicMedictreatRecord.getRegisterNo(), medicMedictreatRecordNew);

            // 复制处方详情

            // 复制结算信息

            // 复制结算详情

            // 复制支付记录

            return;
        }
    }

    private void createMedicPrescription(String registerNo, MedicMedictreatRecord medicMedictreatRecordNew) {
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

        String endStr = StringUtils.leftPad(medicRegisterRecord.getCreateDate().getDay()+"", 2, '0');

        MedicRegisterRecord medicRegisterRecordNew = new MedicRegisterRecord();
        BeanUtils.copyProperties(medicRegisterRecord,medicRegisterRecordNew,"id");

        medicRegisterRecordNew.setRegisterNo(medicRegisterRecord.getRegisterNo() + endStr);
        medicRegisterRecordNew.setCreateDate(DateUtils.addMonths(medicRegisterRecord.getCreateDate(), 1));
        medicRegisterRecordNew.setUpdateDate(DateUtils.addMonths(medicRegisterRecord.getUpdateDate(), 1));

        //添加挂号信息
        medicRegisterRecordDao.save(medicRegisterRecordNew);

        return medicRegisterRecordNew;
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
