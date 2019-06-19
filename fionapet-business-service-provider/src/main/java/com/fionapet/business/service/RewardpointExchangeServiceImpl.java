package com.fionapet.business.service;

import com.fionapet.business.entity.RewardpointExchange;
import com.fionapet.business.repository.RewardpointExchangeDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 赔偿交换 Created by tom on 2016-07-18 11:56:11.
 */

@Service
public class RewardpointExchangeServiceImpl extends CURDServiceBase<RewardpointExchange>
        implements RewardpointExchangeService {

    @Autowired
    private RewardpointExchangeDao rewardpointExchangeDao;

    @Override
    public DaoBase<RewardpointExchange> getDao() {
        return rewardpointExchangeDao;
    }
}
