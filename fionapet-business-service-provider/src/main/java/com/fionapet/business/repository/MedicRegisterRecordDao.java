package com.fionapet.business.repository;

import com.fionapet.business.entity.MedicRegisterRecord;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 医生登记记录明细
* Created by tom on 2016-07-18 11:56:10.
 **/
public interface MedicRegisterRecordDao extends DaoBase<MedicRegisterRecord> {
    MedicRegisterRecord findByRegisterNo(String registerNo);

    List<MedicRegisterRecord> findByPetIdAndPaidStatusNot(String id, String sm00051);

    List<MedicRegisterRecord> findByDoctor(String name);


    @Modifying
    @Query("update MedicRegisterRecord set status ='SM00037' where status <> 'SM00037'")
    void over();
}
