package com.fionapet.business.repository;

import com.fionapet.business.entity.MedicRegisterRecord;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.text.ParseException;
import java.util.List;

/**
 * 医生登记记录明细
* Created by tom on 2016-07-18 11:09:23.
 */
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class MedicRegisterRecordDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private MedicRegisterRecordDao medicRegisterRecordDao;

    @Test
    public void findAll(){
        List<MedicRegisterRecord> medicRegisterRecords = medicRegisterRecordDao.findAllBy();

        Assert.assertNotNull(medicRegisterRecords);

        Assert.assertEquals(medicRegisterRecords.size(), 0);
    }

    @Test
    public void getByPaidStatusAndCreateDateBetweenGroupByDoctor() throws Exception {
        List<String[]> medicRegisterRecords = medicRegisterRecordDao.getByPaidStatusAndCreateDateBetweenGroupByDoctor("SM00051", DateUtils.parseDate("2017-04-14 00:00:00", "yyyy-MM-dd hh:mm:ss"), DateUtils.parseDate("2017-04-14 23:59:59", "yyyy-MM-dd hh:mm:ss"));
        Assert.assertNotNull(medicRegisterRecords);
        Assert.assertEquals(medicRegisterRecords.size(), 2);
    }
}
