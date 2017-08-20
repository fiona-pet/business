package com.fionapet.business.service;

import com.fionapet.business.entity.PrepayMoney;
import org.dubbo.x.service.CURDService;

import java.util.List;

/**
 * 提前付费表
* Created by tom on 2016-07-18 11:56:10.
 */
public interface PrepayMoneyService extends CURDService<PrepayMoney> {

    List<PrepayMoney> findByRelationId(String uuid);
}
