package com.fionapet.business.repository;

import com.fionapet.business.entity.FosterRecord;
import org.dubbo.x.repository.DaoBase;

import java.util.Date;
import java.util.List;

/**
 * 养育记录主表
* Created by tom on 2016-07-25 09:32:32.
 **/
public interface FosterRecordDao extends DaoBase<FosterRecord> {
    List<FosterRecord> findByStatusDictDetailCodeAndCreateDateBetween(String dictDetailCode, Date start, Date end);
}
