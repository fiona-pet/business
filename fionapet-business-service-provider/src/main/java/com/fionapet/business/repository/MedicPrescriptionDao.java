package com.fionapet.business.repository;

import com.fionapet.business.entity.MedicPrescription;
import org.dubbo.x.repository.DaoBase;

import java.util.List;

/**
 * 医生处方明细
* Created by tom on 2016-07-18 11:56:08.
 **/
public interface MedicPrescriptionDao extends DaoBase<MedicPrescription> {
    /**
     * 获取 处方信息
     * @param id
     * @param prescriptionCode
     * @return
     */
    MedicPrescription findByIdOrPrescriptionCode(String id, String prescriptionCode);
    /**
     * 获取 处方信息
     * @param medicRecordId 诊疗信息
     * @param
     * @return
     */
    List<MedicPrescription> findByMedicRecordId(String medicRecordId);
}
