package com.fionapet.business.facade;

import com.fionapet.business.entity.BaseMedicPrescriptionDetail;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.BaseMedicPrescriptionDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 处方明细
* Created by tom on 2016-07-18 15:56:23.
 */
public class BaseMedicPrescriptionDetailRestServiceImpl extends RestServiceBase<BaseMedicPrescriptionDetail> implements BaseMedicPrescriptionDetailRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMedicPrescriptionDetailRestServiceImpl.class);
    @org.springframework.beans.factory.annotation.Autowired
    private BaseMedicPrescriptionDetailService baseMedicPrescriptionDetailService;

    public BaseMedicPrescriptionDetailService getBaseMedicPrescriptionDetailService() {
        return baseMedicPrescriptionDetailService;
    }

    public void setBaseMedicPrescriptionDetailService(BaseMedicPrescriptionDetailService baseMedicPrescriptionDetailService) {
        this.baseMedicPrescriptionDetailService = baseMedicPrescriptionDetailService;
    }

    @Override
    public CURDService<BaseMedicPrescriptionDetail> getService() {
        return baseMedicPrescriptionDetailService;
    }

}
