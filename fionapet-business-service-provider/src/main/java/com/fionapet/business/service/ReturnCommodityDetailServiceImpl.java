package com.fionapet.business.service;

import com.fionapet.business.entity.ReturnCommodityDetail;
import com.fionapet.business.repository.ReturnCommodityDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 返回商品明细 Created by tom on 2016-07-18 11:56:11.
 */

@Service
public class ReturnCommodityDetailServiceImpl extends CURDServiceBase<ReturnCommodityDetail>
        implements ReturnCommodityDetailService {

    @Autowired
    private ReturnCommodityDetailDao returnCommodityDetailDao;

    @Override
    public DaoBase<ReturnCommodityDetail> getDao() {
        return returnCommodityDetailDao;
    }
}
