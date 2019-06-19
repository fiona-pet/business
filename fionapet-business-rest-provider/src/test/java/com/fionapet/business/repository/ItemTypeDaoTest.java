package com.fionapet.business.repository;

import com.fionapet.business.entity.ItemType;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 商品类型
* Created by tom on 2016-07-31 16:36:26.
 */
@ContextConfiguration(locations = {"/applicationContext-service.xml"})
public class ItemTypeDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private ItemTypeDao itemTypeDao;

    @Test
    public void findAll(){
        List<ItemType> itemTypes = itemTypeDao.findAllBy();

        Assert.assertNotNull(itemTypes);

        Assert.assertEquals(itemTypes.size(), 85);
    }

    @Test
    @Rollback(false)
    public void updateAlias() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(new File("/Users/bailijiang/Downloads/药品列表.xls"));
        Workbook rwb = null;
        try {
            rwb = Workbook.getWorkbook(fis);
            Sheet rs = rwb.getSheet(0);
            for (int j = 1; j < rs.getRows(); j++) {
                Cell[] cells = rs.getRow(j);
                if (cells.length > 3) {
                    String alias = cells[3].getContents();
                    if (StringUtils.isNotEmpty(alias)) {
                        ItemType itemType = itemTypeDao.findOne(cells[0].getContents());
                        if (null != itemType) {
                            itemType.setItemAlias(alias);
                            itemTypeDao.save(itemType);
                        }
                    }
                }
            }

            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search(){
        String key = "dc";
        List<ItemType> itemTypes = itemTypeDao.findByInputCodeLikeOrItemNameLikeOrItemCodeLikeOrderByCateNoAsc(key+"%",key+"%",key+"%");

        Assert.assertNotNull(itemTypes);

        System.out.println(itemTypes);
    }
}
