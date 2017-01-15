package com.fionapet.business.service;

import com.fionapet.business.entity.MedicPrescription;
import org.dubbo.x.exception.ApiException;
import org.dubbo.x.service.CURDService;
/**
 * 医生处方明细
* Created by tom on 2016-07-18 11:56:08.
 */
public interface MedicPrescriptionService extends CURDService<MedicPrescription> {

    /**
     * 处方复制
     * @param id 处方编号或id
     * @param medicRecordCode 就诊编号
     * @return
     */
    MedicPrescription copy(String id, String medicRecordCode) throws ApiException;

}
