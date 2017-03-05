package com.fionapet.business.repository;

import com.fionapet.business.entity.StoreDirectSell;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.util.List;

/**
 * 仓库直销主表
* Created by tom on 2016-07-18 11:00:46.
 */
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})
public class StoreDirectSellDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private StoreDirectSellDao storeDirectSellDao;



    @Test
    @Rollback(false)
    public void findAll(){
        List<StoreDirectSell> storeDirectSells = storeDirectSellDao.findAllBy();

        Assert.assertNotNull(storeDirectSells);

        Assert.assertEquals(storeDirectSells.size(), 124);
        int start = 713;
        for (StoreDirectSell storeDirectSell: storeDirectSells){
            if (null == storeDirectSell.getDirectSellCode()){
                storeDirectSell.setDirectSellCode("XS" + StringUtils.leftPad(start++ + "", 5, "0"));
                storeDirectSellDao.save(storeDirectSell);
            }

        }
    }
}
