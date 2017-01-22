package com.fionapet.business.service;

import com.fionapet.business.entity.Gest;
import com.fionapet.business.facade.vo.RechargeVO;
import org.dubbo.x.exception.ApiException;
import org.dubbo.x.service.CURDService;
/**
 * 会员
* Created by tom on 2016-07-22 14:21:59.
 */
public interface GestService extends CURDService<Gest> {
    /**
     * 会员充值
     * @param id
     * @param money
     * @return
     */
    RechargeVO recharge(String id, Double money)throws ApiException;

}
