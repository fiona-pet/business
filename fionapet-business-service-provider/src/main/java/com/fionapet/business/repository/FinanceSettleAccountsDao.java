package com.fionapet.business.repository;

import com.fionapet.business.entity.FinanceSettleAccounts;
import org.dubbo.x.repository.DaoBase;

import java.util.List;
import java.util.Set;

/**
 * 资金账号表
* Created by tom on 2016-07-25 09:32:33.
 **/
public interface FinanceSettleAccountsDao extends DaoBase<FinanceSettleAccounts> {
    List<FinanceSettleAccounts> findByIdIn(Set<String> ids);
}
