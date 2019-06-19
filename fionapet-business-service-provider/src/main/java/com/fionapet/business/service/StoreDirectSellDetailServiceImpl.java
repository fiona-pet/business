package com.fionapet.business.service;

import com.fionapet.business.entity.StoreDirectSellDetail;
import com.fionapet.business.repository.StoreDirectSellDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 仓库直销明细 Created by tom on 2016-07-18 11:56:11.
 */

@Service
public class StoreDirectSellDetailServiceImpl extends CURDServiceBase<StoreDirectSellDetail>
        implements StoreDirectSellDetailService {

    @Autowired
    private StoreDirectSellDetailDao storeDirectSellDetailDao;

    @Override
    public DaoBase<StoreDirectSellDetail> getDao() {
        return storeDirectSellDetailDao;
    }
}
