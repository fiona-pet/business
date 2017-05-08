package com.fionapet.business.repository;

import com.fionapet.business.entity.AppConfig;
import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * 报表统计
* Created by tom on 2016-07-13 15:23:53.
 */
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class ReportDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ReportByItemDao reportByItemDao;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void findAll(){
        List<ReportByPersonVO> reportByPersonVOs = reportDao.findAllBy();

        Assert.assertNotNull(reportByPersonVOs);

        Assert.assertEquals(reportByPersonVOs.size(), 337);
    }

    @Test
    public void findCreateDateBetween(){
        List<ReportByPersonVO> reportByPersonVOs = reportDao.findCreateDateBetween("2017-03-01", "2017-03-31");
        Assert.assertNotNull(reportByPersonVOs);

        Assert.assertEquals(reportByPersonVOs.size(), 6);
    }

    @Test
    public void findByNameAndCreateDateLike(){
        List<ReportByPersonVO> reportByPersonVOs = reportDao.findByNameAndCreateDateLike("席雨明", "2017-04%");
        Assert.assertNotNull(reportByPersonVOs);

        Assert.assertEquals(reportByPersonVOs.size(), 22);
    }

    @Test
    public void findByCreateDateBetweenOrderByTotalDesc(){
        List<ReportByItemVO> reportByItemVOs = reportByItemDao.findByCreateDateOrderByTotalDesc("2017-03");
        Assert.assertNotNull(reportByItemVOs);

        Assert.assertEquals(reportByItemVOs.size(), 6);
    }




//    @Test
//    public void currentMaxCode(){
//        String prefix = "HYDJ";
//        int length = 5;
//
//        Query query = entityManagerFactory.createEntityManager().createNativeQuery(String.format("select max(%s) from %s", "level_code", "t_gest_level"));
//        Object result = query.getSingleResult();
//        Assert.assertNotNull(result);
//        System.out.println(result);
//
//        if (null!=result){
//            String numberCodeStr = result+"";
//            numberCodeStr = numberCodeStr.replaceFirst(prefix,"");
//
//            long numberCode = Long.parseLong(numberCodeStr) + 1;
//
//            numberCodeStr = prefix + StringUtils.leftPad(numberCode + "", 5, "0");
//
//            System.out.println(numberCodeStr);
//        }


//
//        List<Object[]> currentMaxCode = appConfigDao.currentMaxCode("gest_code", "t_gest");
//
//        Assert.assertNull(currentMaxCode);
//    }
}
