package com.fionapet.business.service;

import com.fionapet.business.entity.Dealer;
import com.fionapet.business.repository.DealerDao;
import org.dubbo.x.repository.DaoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 代理商 Created by tom on 2016-07-31 16:42:51.
 */

@Transactional
@Service
public class DealerServiceImpl extends StatusCURDServiceBase<Dealer> implements DealerService {

    @Autowired
    private DealerDao dealerDao;

    @Override
    public DaoBase<Dealer> getDao() {
        return dealerDao;
    }
}
