package com.fionapet.business.repository;

import com.fionapet.business.entity.PrepayMoney;
import org.dubbo.x.repository.DaoBase;

import java.util.List;

/**
 * 提前付费表
* Created by tom on 2016-07-18 11:56:10.
 **/
public interface PrepayMoneyDao extends DaoBase<PrepayMoney> {
    List<PrepayMoney> findByRelationId(String uuid);
}
