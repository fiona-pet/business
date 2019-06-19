package com.fionapet.business.service;

import com.fionapet.business.entity.TrprescriptionTemplate;
import com.fionapet.business.repository.TrprescriptionTemplateDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 药方模版 Created by tom on 2016-07-31 16:36:25.
 */

@Service
public class TrprescriptionTemplateServiceImpl extends CURDServiceBase<TrprescriptionTemplate>
        implements TrprescriptionTemplateService {

    @Autowired
    private TrprescriptionTemplateDao trprescriptionTemplateDao;

    @Override
    public DaoBase<TrprescriptionTemplate> getDao() {
        return trprescriptionTemplateDao;
    }
}
