package com.fionapet.business.repository;

import com.fionapet.business.entity.GestPaidRecord;
import com.fionapet.business.entity.BillVO;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 顾客影像记录表
* Created by tom on 2016-07-25 09:32:34.
 */
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class GestPaidRecordDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private GestPaidRecordDao gestPaidRecordDao;

    @Test
    public void findAll(){
        List<GestPaidRecord> gestPaidRecords = gestPaidRecordDao.findAllBy();

        Assert.assertNotNull(gestPaidRecords);

        Assert.assertEquals(gestPaidRecords.size(), 0);
    }

    @Test
    public void getReportForOperateAction() throws ParseException {
        Date start = DateUtils.parseDate("2017-04-01", "yyyy-MM-dd");
        Date end = DateUtils.parseDate("2017-04-01", "yyyy-MM-dd");

        System.out.println(start.toLocaleString());
        List<String[]> gestPaidRecords = gestPaidRecordDao.getReportForOperateAction(start, end);
        System.out.println(gestPaidRecords);
//
//        Assert.assertNotNull(gestPaidRecords);
//
//        Assert.assertEquals(gestPaidRecords.size(), 4);
    }
}
