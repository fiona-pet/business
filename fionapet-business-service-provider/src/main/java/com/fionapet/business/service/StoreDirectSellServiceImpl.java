package com.fionapet.business.service;

import com.fionapet.business.entity.StoreDirectSell;
import com.fionapet.business.repository.StoreDirectSellDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仓库直销主表 Created by tom on 2016-07-18 11:56:09.
 */

@Service
public class StoreDirectSellServiceImpl extends CURDServiceBase<StoreDirectSell>
        implements StoreDirectSellService {

    @Autowired
    private StoreDirectSellDao storeDirectSellDao;

    @Override
    public DaoBase<StoreDirectSell> getDao() {
        return storeDirectSellDao;
    }
}
