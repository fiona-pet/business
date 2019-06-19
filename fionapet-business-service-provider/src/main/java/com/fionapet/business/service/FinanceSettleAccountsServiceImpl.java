package com.fionapet.business.service;

import com.fionapet.business.entity.FinanceSettleAccounts;
import com.fionapet.business.entity.GestPaidRecord;
import com.fionapet.business.repository.FinanceSettleAccountsDao;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 资金账号表 Created by tom on 2016-07-25 09:32:33.
 */

@Service
public class FinanceSettleAccountsServiceImpl extends CURDServiceBase<FinanceSettleAccounts>
        implements FinanceSettleAccountsService {

    @Autowired
    private FinanceSettleAccountsDao financeSettleAccountsDao;
    @Autowired
    private GestPaidRecordService gestPaidRecordService;

    @Override
    public DaoBase<FinanceSettleAccounts> getDao() {

        return financeSettleAccountsDao;
    }

    @Override
    public Page<FinanceSettleAccounts> page(PageSearch pageSearch) {
        Page<FinanceSettleAccounts> page = super.page(pageSearch);
        for (FinanceSettleAccounts financeSettleAccounts : page.getContent()) {
            GestPaidRecord
                    paidRecord =
                    gestPaidRecordService.getPaidType(financeSettleAccounts.getId());
            if (null != paidRecord) {
                financeSettleAccounts.setPaidType(paidRecord.getOperateAction());
            }
        }

        return page;
    }
}
