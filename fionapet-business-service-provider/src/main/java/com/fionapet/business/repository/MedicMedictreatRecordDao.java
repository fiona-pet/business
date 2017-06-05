package com.fionapet.business.repository;

import com.fionapet.business.entity.MedicMedictreatRecord;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 医生处理记录
* Created by tom on 2016-07-18 11:56:10.
 **/
public interface MedicMedictreatRecordDao extends DaoBase<MedicMedictreatRecord> {
    MedicMedictreatRecord findByRegisterNo(String registerNo);

    MedicMedictreatRecord findByMediTreatmentCode(String medicRecordCode);

    MedicMedictreatRecord findByRegisterNoOrMediTreatmentCode(String registerNo,String medicRecordCode);

    @Modifying
    @Query("update MedicMedictreatRecord set status ='SM00037' where status <> 'SM00037'")
    void over();

    long countByMediTreatmentCode(String mediTreatmentCode);
}
