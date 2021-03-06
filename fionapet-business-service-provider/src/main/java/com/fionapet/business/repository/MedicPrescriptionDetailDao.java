package com.fionapet.business.repository;

import com.fionapet.business.entity.MedicPrescriptionDetail;
import org.dubbo.x.repository.DaoBase;

import java.util.List;

/**
 * 医生处方明细
* Created by tom on 2016-07-18 11:56:10.
 **/
public interface MedicPrescriptionDetailDao extends DaoBase<MedicPrescriptionDetail> {
    List<MedicPrescriptionDetail> findByPrescriptionId(String prescriptionId);
}
