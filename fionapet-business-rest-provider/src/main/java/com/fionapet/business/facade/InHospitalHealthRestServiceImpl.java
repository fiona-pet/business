package com.fionapet.business.facade;

import com.fionapet.business.entity.InHospitalHealth;
import com.fionapet.business.service.InHospitalHealthService;
import org.dubbo.x.facade.RestServiceBase;
import org.dubbo.x.service.CURDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 养育健康状况表
* Created by tom on 2016-07-25 09:32:32.
 */
public class InHospitalHealthRestServiceImpl extends RestServiceBase<InHospitalHealth> implements InHospitalHealthRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InHospitalHealthRestServiceImpl.class);
    @org.springframework.beans.factory.annotation.Autowired
    private InHospitalHealthService InHospitalHealthService;

    public InHospitalHealthService getInHospitalHealthService() {
        return InHospitalHealthService;
    }

    public void setInHospitalHealthService(InHospitalHealthService InHospitalHealthService) {
        this.InHospitalHealthService = InHospitalHealthService;
    }

    @Override
    public CURDService<InHospitalHealth> getService() {
        return InHospitalHealthService;
    }

}
