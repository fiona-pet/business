package com.fionapet.business.service;

import com.fionapet.business.entity.ItemCountChangeReason;
import com.fionapet.business.repository.ItemCountChangeReasonDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 种类数量更改原因表 Created by tom on 2016-07-25 09:32:31.
 */

@Service
public class ItemCountChangeReasonServiceImpl extends CURDServiceBase<ItemCountChangeReason>
        implements ItemCountChangeReasonService {

    @Autowired
    private ItemCountChangeReasonDao itemCountChangeReasonDao;

    @Override
    public DaoBase<ItemCountChangeReason> getDao() {
        return itemCountChangeReasonDao;
    }
}
