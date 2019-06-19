package com.fionapet.business.service;

import com.fionapet.business.entity.ItemType;
import com.fionapet.business.facade.vo.ItemTypeVO;
import com.fionapet.business.repository.ItemCountDao;
import com.fionapet.business.repository.ItemTypeDao;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品类型 Created by tom on 2016-07-31 16:36:26.
 */

@Service
public class ItemTypeServiceImpl extends CURDServiceBase<ItemType> implements ItemTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemTypeServiceImpl.class);
    @Autowired
    private ItemTypeDao itemTypeDao;

    @Autowired
    private ItemCountDao itemCountDao;

    @Override
    public DaoBase<ItemType> getDao() {
        return itemTypeDao;
    }

    @Override
    public List<ItemTypeVO> search(String key) {
        List<ItemType> itemTypes = null;

        if (StringUtils.isEmpty(key)) {
            itemTypes = itemTypeDao.findAllBy();
        } else {
            String s = StringUtils.upperCase(key) + "%";
            itemTypes =
                    itemTypeDao
                            .findByInputCodeLikeOrItemNameLikeOrItemCodeLikeOrderByCateNoAsc(s, s,
                                                                                             s);
        }

        List<ItemTypeVO> result = new ArrayList<ItemTypeVO>();

        for (ItemType itemType : itemTypes) {
            ItemTypeVO vo = new ItemTypeVO();

            try {
                BeanUtilsBean.getInstance().copyProperties(vo, itemType);
            } catch (Exception e) {
                LOGGER.info("copy property error!", e);
            }

            result.add(vo);
        }

        return result;
    }

//    public List<ItemType> listAll() {
//        List<ItemType> itemTypes = new ArrayList<ItemType>();
//        for (ItemType itemType: super.listAll()){
//            ItemCount itemCount = itemCountDao.findByItemCode(itemType.getItemCode());
//            if (null !=itemCount){
//                itemType.setItemName(itemType.getItemName() +"[" +  itemCount.getItemCountNum() + "/" + itemCount.getScatteredCountNum()+ "]");
//            }
//            itemTypes.add(itemType);
//        }
//        return itemTypes;
//    }
}
