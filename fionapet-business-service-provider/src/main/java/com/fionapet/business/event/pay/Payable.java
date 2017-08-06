package com.fionapet.business.event.pay;

import com.fionapet.business.entity.SettleAccountsView;

/**
 * Created by tom on 2017/8/6.
 */
public interface Payable {
    public void pay(SettleAccountsView settleAccountsView);
    public void decrease(SettleAccountsView settleAccountsView);
}
