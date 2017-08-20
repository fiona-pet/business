package com.fionapet.business.repository;

import com.fionapet.business.entity.FosterRecordDetail;
import org.dubbo.x.repository.DaoBase;

import java.util.List;

/**
 * 养育记录明细表
* Created by tom on 2016-07-25 09:32:31.
 **/
public interface FosterRecordDetailDao extends DaoBase<FosterRecordDetail> {
    List<FosterRecordDetail> findByFosterId(String uuid);
}
