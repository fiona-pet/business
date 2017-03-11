package com.fionapet.business.service;

import com.fionapet.business.entity.FinanceSettleAccounts;
import com.fionapet.business.entity.GestPaidRecord;
import com.fionapet.business.entity.SettleAccountsView;
import com.fionapet.business.exception.ApiException;
import com.fionapet.business.entity.BillVO;
import com.fionapet.business.facade.vo.PayVO;
import org.dubbo.x.service.CURDService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 顾客影像记录表
* Created by tom on 2016-07-25 09:32:34.
 */
public interface GestPaidRecordService extends CURDService<GestPaidRecord> {

    List<BillVO> billList(String timestamp);

    List<SettleAccountsView> billDetail(String gestId);

    FinanceSettleAccounts pay(PayVO pay) throws ApiException;
}
