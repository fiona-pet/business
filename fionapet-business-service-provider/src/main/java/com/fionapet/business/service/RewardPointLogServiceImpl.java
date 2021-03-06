package com.fionapet.business.service;

import com.fionapet.business.entity.RewardPointLog;
import com.fionapet.business.repository.RewardPointLogDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 序列号 Created by tom on 2016-07-18 11:56:09.
 */

@Service
public class RewardPointLogServiceImpl extends CURDServiceBase<RewardPointLog>
        implements RewardPointLogService {

    @Autowired
    private RewardPointLogDao rewardPointLogDao;

    @Override
    public DaoBase<RewardPointLog> getDao() {
        return rewardPointLogDao;
    }
}
