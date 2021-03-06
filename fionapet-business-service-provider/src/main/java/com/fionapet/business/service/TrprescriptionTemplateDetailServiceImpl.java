package com.fionapet.business.service;

import com.fionapet.business.entity.TrprescriptionTemplateDetail;
import com.fionapet.business.repository.TrprescriptionTemplateDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 药方模版明细 Created by tom on 2016-07-31 16:42:51.
 */

@Service
public class TrprescriptionTemplateDetailServiceImpl
        extends CURDServiceBase<TrprescriptionTemplateDetail>
        implements TrprescriptionTemplateDetailService {

    @Autowired
    private TrprescriptionTemplateDetailDao trprescriptionTemplateDetailDao;

    @Override
    public DaoBase<TrprescriptionTemplateDetail> getDao() {
        return trprescriptionTemplateDetailDao;
    }
}
