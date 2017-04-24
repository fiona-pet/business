package com.fionapet.business.repository;

import com.fionapet.business.entity.InputMoneyRecord;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * 收款记录表
* Created by tom on 2016-07-25 09:32:32.
 **/
public interface InputMoneyRecordDao extends DaoBase<InputMoneyRecord> {
    @Query(value = "select total from (SELECT sum(input_money) total, (input_money > 0) input_type FROM t_input_money_record WHERE create_date >= ?1 and create_date <= ?2 GROUP BY (input_money > 0)  ) s where s.input_type = ?3", nativeQuery = true)
    Long countInputMoneyByCreateAndIsInput(Date start, Date end, int i);
}
