package com.fionapet.business.repository;

import com.fionapet.business.entity.FinanceSettleAccountsDetail;
import org.dubbo.x.repository.DaoBase;

import java.util.List;
import java.util.Set;

/**
 * 资金账户明细表
* Created by tom on 2016-07-25 09:32:33.
 **/
public interface FinanceSettleAccountsDetailDao extends DaoBase<FinanceSettleAccountsDetail> {
    List<FinanceSettleAccountsDetail> findByRelationIdIn(Set<String> prescriptionIdsOld);

    FinanceSettleAccountsDetail findOneByRelationIdAndRelationDetailId(String medicPrescriptionId, String medicPrescriptionDetailId);

    List<FinanceSettleAccountsDetail> findByRelationId(String medicPrescriptionIdOld);
}
