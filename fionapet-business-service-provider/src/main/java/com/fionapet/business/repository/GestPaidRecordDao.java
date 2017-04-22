package com.fionapet.business.repository;

import com.fionapet.business.entity.GestPaidRecord;
import com.fionapet.business.entity.BillVO;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 顾客影像记录表
* Created by tom on 2016-07-25 09:32:34.
 **/
public interface GestPaidRecordDao extends DaoBase<GestPaidRecord> {
    @Query(value = "select sum(fsa.should_paid_money), operate_action from t_gest_paid_record gpr join t_finance_settle_accounts fsa on gpr.settle_accounts_id=fsa.id where gpr.create_date >=?1 and gpr.create_date < ?2 group by operate_action ",nativeQuery = true)
    List<String[]> getReportForOperateAction(Date start, Date end);

    GestPaidRecord findBySettleAccountsId(String id);
}
