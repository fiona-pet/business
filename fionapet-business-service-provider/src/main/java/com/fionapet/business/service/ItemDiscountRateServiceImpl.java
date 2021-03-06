package com.fionapet.business.service;

import com.fionapet.business.entity.ItemDiscountRate;
import com.fionapet.business.repository.ItemDiscountRateDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务类型打折率 Created by tom on 2016-07-31 16:42:51.
 */

@Service
public class ItemDiscountRateServiceImpl extends CURDServiceBase<ItemDiscountRate>
        implements ItemDiscountRateService {

    @Autowired
    private ItemDiscountRateDao itemDiscountRateDao;

    @Override
    public DaoBase<ItemDiscountRate> getDao() {
        return itemDiscountRateDao;
    }
}
