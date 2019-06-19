package com.fionapet.business.service;

import com.fionapet.business.entity.FosterRecordDetail;
import com.fionapet.business.repository.FosterRecordDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 养育记录明细表 Created by tom on 2016-07-25 09:32:31.
 */

@Service
public class FosterRecordDetailServiceImpl extends CURDServiceBase<FosterRecordDetail>
        implements FosterRecordDetailService {

    @Autowired
    private FosterRecordDetailDao fosterRecordDetailDao;

    @Override
    public DaoBase<FosterRecordDetail> getDao() {
        return fosterRecordDetailDao;
    }

    @Override
    public FosterRecordDetail createOrUpdte(FosterRecordDetail entity) {
        return super.createOrUpdte(entity);
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
    }
}
