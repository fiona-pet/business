package com.fionapet.business.facade;

import com.fionapet.business.entity.RewardPointLog;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.RewardPointLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 序列号
* Created by tom on 2016-07-18 11:56:09.
 */
public class RewardPointLogRestServiceImpl extends RestServiceBase<RewardPointLog> implements RewardPointLogRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RewardPointLogRestServiceImpl.class);

    @Autowired
    private RewardPointLogService rewardPointLogService;

    public RewardPointLogService getRewardPointLogService() {
        return rewardPointLogService;
    }

    public void setRewardPointLogService(RewardPointLogService rewardPointLogService) {
        this.rewardPointLogService = rewardPointLogService;
    }

    @Override
    public CURDService<RewardPointLog> getService() {
        return rewardPointLogService;
    }

}
