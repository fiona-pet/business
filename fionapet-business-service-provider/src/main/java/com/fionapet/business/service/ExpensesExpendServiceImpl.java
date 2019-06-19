package com.fionapet.business.service;

import com.fionapet.business.entity.ExpensesExpend;
import com.fionapet.business.repository.ExpensesExpendDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 付出费用表 Created by tom on 2016-07-25 09:32:34.
 */

@Service
public class ExpensesExpendServiceImpl extends CURDServiceBase<ExpensesExpend>
        implements ExpensesExpendService {

    @Autowired
    private ExpensesExpendDao expensesExpendDao;

    @Override
    public DaoBase<ExpensesExpend> getDao() {
        return expensesExpendDao;
    }
}
