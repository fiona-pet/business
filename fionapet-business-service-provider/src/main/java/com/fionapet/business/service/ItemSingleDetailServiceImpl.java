package com.fionapet.business.service;

import com.fionapet.business.entity.ItemSingleDetail;
import com.fionapet.business.repository.ItemSingleDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类型化验实例明细表 Created by tom on 2016-07-25 09:32:32.
 */

@Service
public class ItemSingleDetailServiceImpl extends CURDServiceBase<ItemSingleDetail>
        implements ItemSingleDetailService {

    @Autowired
    private ItemSingleDetailDao itemSingleDetailDao;

    @Override
    public DaoBase<ItemSingleDetail> getDao() {
        return itemSingleDetailDao;
    }
}
