package com.fionapet.business.facade;

import com.fionapet.business.entity.MedicPrescription;
import io.swagger.annotations.ApiParam;
import org.dubbo.x.exception.ApiException;
import org.dubbo.x.facade.RestResult;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.facade.RestServiceBase;
import com.fionapet.business.service.MedicPrescriptionService;
import org.dubbo.x.util.ConstantVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;

/**
 * 医生处方明细
* Created by tom on 2016-07-18 11:56:08.
 */
public class MedicPrescriptionRestServiceImpl extends RestServiceBase<MedicPrescription> implements MedicPrescriptionRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicPrescriptionRestServiceImpl.class);

    private MedicPrescriptionService medicPrescriptionService;

    public MedicPrescriptionService getMedicPrescriptionService() {
        return medicPrescriptionService;
    }

    public void setMedicPrescriptionService(MedicPrescriptionService medicPrescriptionService) {
        this.medicPrescriptionService = medicPrescriptionService;
    }

    @Override
    public CURDService<MedicPrescription> getService() {
        return medicPrescriptionService;
    }

    @Override
    public RestResult<MedicPrescription> copy(@HeaderParam(ConstantVariable.HEADER_AUTHORIZATION_KEY) String token, @ApiParam("id") String uuid, @ApiParam("regmedicRecordCode") String medicRecordCode) throws ApiException {
        return RestResult.OK(((MedicPrescriptionService)getService()).copy(uuid,medicRecordCode));
    }


}
