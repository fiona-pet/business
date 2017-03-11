package com.fionapet.business.repository;

import com.fionapet.business.entity.BillVO;
import com.fionapet.business.entity.SettleAccountsView;
import org.dubbo.x.repository.DaoBase;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * 待支付
* Created by tom on 2016-07-25 09:32:34.
 **/
public interface BillVODao extends DaoBase<BillVO> {
    @Override
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="false") })
    List<BillVO> findAllBy();
}
