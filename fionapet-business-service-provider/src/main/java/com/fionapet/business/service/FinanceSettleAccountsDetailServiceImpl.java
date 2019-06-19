package com.fionapet.business.service;

import com.fionapet.business.entity.FinanceSettleAccountsDetail;
import com.fionapet.business.repository.FinanceSettleAccountsDetailDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资金账户明细表 Created by tom on 2016-07-25 09:32:33.
 */

@Service
public class FinanceSettleAccountsDetailServiceImpl
        extends CURDServiceBase<FinanceSettleAccountsDetail>
        implements FinanceSettleAccountsDetailService {

    @Autowired
    private FinanceSettleAccountsDetailDao financeSettleAccountsDetailDao;

    @Override
    public DaoBase<FinanceSettleAccountsDetail> getDao() {
        return financeSettleAccountsDetailDao;
    }
}
