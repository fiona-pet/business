package com.fionapet.business.service;

import com.fionapet.business.entity.PrescriptionTemplate;
import com.fionapet.business.util.Pinyin4Utils;
import org.apache.commons.lang3.StringUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.PrescriptionTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  处方模版
* Created by tom on 2016-07-31 16:42:52.
 */
public class PrescriptionTemplateServiceImpl extends CURDServiceBase<PrescriptionTemplate> implements PrescriptionTemplateService {
    @Autowired
    private PrescriptionTemplateDao prescriptionTemplateDao;

    @Override
    public DaoBase<PrescriptionTemplate> getDao() {
        return prescriptionTemplateDao;
    }

    @Override
    public PrescriptionTemplate createOrUpdte(PrescriptionTemplate entity) {

        if (null != entity && StringUtils.isEmpty(entity.getBarCode())){
            entity.setBarCode(Pinyin4Utils.getFirstSpell(entity.getTemplateName()));
        }

        return super.createOrUpdte(entity);
    }
}
