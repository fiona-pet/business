package com.fionapet.business.service;

import com.fionapet.business.entity.ItemType;
import com.sun.tools.javac.util.StringUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.ItemTypeDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  商品类型
* Created by tom on 2016-07-31 16:36:26.
 */
public class ItemTypeServiceImpl extends CURDServiceBase<ItemType> implements ItemTypeService {
    @Autowired
    private ItemTypeDao itemTypeDao;

    @Override
    public DaoBase<ItemType> getDao() {
        return itemTypeDao;
    }

    @Override
    public List<ItemType> search(String key) {
        String s = StringUtils.toUpperCase(key) + "%";
        List<ItemType> result = itemTypeDao.findByInputCodeLikeOrItemNameLikeOrItemCodeLikeOrderByCateNoAsc(s, s, s);
        return result;
    }
}
