package com.fionapet.business.service;

import com.fionapet.business.entity.ItemtypeSupplies;
import com.fionapet.business.repository.ItemtypeSuppliesDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 供应量表 Created by tom on 2016-07-25 09:32:33.
 */

@Service
public class ItemtypeSuppliesServiceImpl extends CURDServiceBase<ItemtypeSupplies>
        implements ItemtypeSuppliesService {

    @Autowired
    private ItemtypeSuppliesDao itemtypeSuppliesDao;

    @Override
    public DaoBase<ItemtypeSupplies> getDao() {
        return itemtypeSuppliesDao;
    }
}
