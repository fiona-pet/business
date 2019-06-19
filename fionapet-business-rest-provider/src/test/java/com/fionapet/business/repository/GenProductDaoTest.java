package com.fionapet.business.repository;

import com.fionapet.business.entity.AppConfig;
import com.fionapet.business.entity.ItemType;
import com.fionapet.business.entity.StatusEntity;
import com.fionapet.business.entity.UserDictDetail;
import com.fionapet.business.util.ExcelUtils;
import com.fionapet.business.util.Pinyin4Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.*;

/**
 * 应用配置
* Created by tom on 2016-07-13 15:23:53.
 */
@ContextConfiguration(locations = {"/META-INF/spring/applicationContext.xml"})

public class GenProductDaoTest extends SpringTransactionalTestCase {

    @Autowired
    private ItemTypeDao itemTypeDao;

    @Autowired
    private UserDictDetailDao userDictDetailDao;

    private static final Map<Integer, Set<String>> CACHE = new HashMap<Integer, Set<String>>();


    @Test
    public void show(){
        int index= 0;
        hasExist(index);
    }
    public void hasExist(Integer sheet){
        String file = "福莱－常用药品－20161106.xls";
        String sourceFile = "products.xls";
        Set data = CACHE.get(sheet);
        if (null == data){
            data = new HashSet<String>();
            List<List<String>> sourceData = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(sourceFile), 0);
            for (List<String> row: sourceData){
                data.add(row.get(0));
            }
            CACHE.put(sheet, data);
        }

        System.out.println(data);

        List<List<String>> newData = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),2);
        for (List<String> row: newData){
            String name = row.get(1);
            if (!StringUtils.isEmpty(name) && !data.contains(name)){
                String str = row.get(1) + "\t"+ row.get(2) + "*" + row.get(3) + "";
                if (str.endsWith("*")){
                    str = str.substring(0, str.length()-1);
                }

                //销售单位
                String sellPrice = str.substring(str.length()-1, str.length());
                str = str + "\t" + sellPrice;


                //处方单位
                String pPrice = row.get(4);
                str = str + "\t" + pPrice;

                //零售比
                int lsb = 1;
                if (sellPrice.equals(pPrice)) {
                    str = str + "\t" + "1";
                }else{
                    if (row.get(3).indexOf(pPrice) > -1) {
                        lsb = Integer.parseInt(row.get(3).substring(0, row.get(3).indexOf(pPrice)));
                        str = str + "\t" + row.get(3).substring(0, row.get(3).indexOf(pPrice));
                    }else{
                        str = str + "\t" + row.get(3);
                    }
                }
                //处方价格
                str = str + "\t" + row.get(6);
                //进价
                try {
                    str = str + "\t" + lsb * Double.parseDouble(row.get(5));
                }catch (Exception e){
                    str = str + "\t" + row.get(5);
                }


                System.out.println(str);

            }
//            System.out.println(name);
        }
    }

    @Test
    @Rollback(false)
    public void gen(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file));

        for (List<String> row: data){
            List<ItemType> itemTypes = itemTypeDao.findByItemName(row.get(0));
            ItemType itemType = new ItemType();

            for (ItemType item:itemTypes){
                if (item.getItemStandard()!= null && item.getItemStandard().equals(row.get(1))){
                    itemType = item;
                    break;
                }
            }

            itemType.setItemName(row.get(0));
            itemType.setInputCode(Pinyin4Utils.getFirstSpell(row.get(0)));
            itemType.setInputPrice(Double.parseDouble(StringUtils.defaultIfEmpty(row.get(6),"0")));
            itemType.setSellPrice(Double.parseDouble(StringUtils.defaultIfEmpty(row.get(7),"0")));
            itemType.setRecipePrice(Double.parseDouble(StringUtils.defaultIfEmpty(row.get(5),"0")));
            itemType.setItemBulk(Integer.parseInt(StringUtils.defaultIfEmpty(row.get(4),"1")));
            itemType.setItemStandard(row.get(1));
            itemType.setCateNo("ICate01");
            itemType.setPackageUnit(getUserDictDetailNo(row.get(2)));
            itemType.setRecipeUnit(getUserDictDetailNo(row.get(3)));
            itemType.setBarCode(row.get(9));
            itemType.setItemCode(row.get(10));
            itemType.setStatus(StatusEntity.DEFAULT());

            itemType.setCreateUserId("34ff5c2e-6625-4d6d-ad39-d1745755b3a8");
            itemType.setUpdateUserId("34ff5c2e-6625-4d6d-ad39-d1745755b3a8");

            System.out.println(itemType);

            itemTypeDao.save(itemType);
        }
    }

    @Test
    @Rollback(false)
    /**
     * 化验项目
     */
    public void genChemicalExam(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),3);

        for (List<String> row: data){
            ItemType itemType  = toItemType(row, "ICate04");
            itemTypeDao.save(itemType);
        }
    }

    @Test
    @Rollback(false)
    /**
     * 手术
     */
    public void genSurgery(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),6);

        for (List<String> row: data){
            ItemType itemType  = toItemType(row, "ICate07");

            itemTypeDao.save(itemType);
        }
    }

    @Test
    @Rollback(false)
    /**
     * 医疗处置
     */
    public void genHandle(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),7);

        for (List<String> row: data){
            ItemType itemType  = toItemType(row, "ICate08");

            itemTypeDao.save(itemType);
        }
    }

    @Test
    @Rollback(false)
    /**
     * 挂号
     */
    public void genRegister(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),9);

        for (List<String> row: data){
            ItemType itemType  = toItemType(row, "ICate10");

            itemTypeDao.save(itemType);
        }
    }
    @Test
    @Rollback(false)
    /**
     * 美容
     */
    public void genFacial(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),11);

        for (List<String> row: data){
            ItemType itemType  = toItemType(row, "ICate12");

            itemTypeDao.save(itemType);
        }
    }


    @Test
    @Rollback(false)
    /**
     * 宠物 食品
     */
    public void genFood(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),1);

        for (List<String> row: data){
            ItemType itemType  = toItemType(row, "ICate02");

            itemTypeDao.save(itemType);
        }
    }

    @Test
    @Rollback(false)
    /**
     * 宠物 用品
     */
    public void genUsed(){
        String file = "products.xls";

        List<List<String>> data = ExcelUtils.toList(GenProductDaoTest.class.getClassLoader().getResourceAsStream(file),2);

        for (List<String> row: data){
            ItemType itemType  = toItemType(row, "ICate03");

            itemTypeDao.save(itemType);
        }
    }


    private ItemType toItemType(List<String> row, String cate){
        List<ItemType> itemTypes = itemTypeDao.findByItemName(row.get(0));
        ItemType itemType = new ItemType();

        for (ItemType item:itemTypes){
            if (StringUtils.equals(item.getItemCode(), row.get(7))){
                itemType = item;
                break;
            }
        }

        itemType.setItemName(row.get(0));
        itemType.setInputCode(Pinyin4Utils.getFirstSpell(row.get(0)));
        itemType.setItemStandard(row.get(1));
//            itemType.setInputPrice(Double.parseDouble(StringUtils.defaultIfEmpty(row.get(6),"0")));
//            itemType.setSellPrice(Double.parseDouble(StringUtils.defaultIfEmpty(row.get(7),"0")));
        itemType.setRecipePrice(Double.parseDouble(StringUtils.defaultIfEmpty(row.get(15),"0")));
//            itemType.setItemBulk(Integer.parseInt(StringUtils.defaultIfEmpty(row.get(4),"0")));
//            itemType.setItemStandard(row.get(1));
        itemType.setCateNo(cate);
//            itemType.setPackageUnit(getUserDictDetailNo(row.get(2)));
        itemType.setRecipeUnit(row.get(14));
        itemType.setBarCode(row.get(6));
        itemType.setItemCode(row.get(7));
        itemType.setStatus(StatusEntity.DEFAULT());

        itemType.setCreateUserId("34ff5c2e-6625-4d6d-ad39-d1745755b3a8");
        itemType.setUpdateUserId("34ff5c2e-6625-4d6d-ad39-d1745755b3a8");

        System.out.println(itemType);

        return itemType;
    }

    private String getUserDictDetailNo(String name){
        List<UserDictDetail> userDictDetail = userDictDetailDao.findByValueNameCn(name);
        if (null != userDictDetail && userDictDetail.size()>0){
            return userDictDetail.get(0).getDictDetailCode();
        }
        return "";
    }

}
