package com.fionapet.business.service;

import com.fionapet.business.entity.MicroSmsConfig;
import com.fionapet.business.repository.MicroSmsConfigDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息提醒配置表 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class MicroSmsConfigServiceImpl extends CURDServiceBase<MicroSmsConfig>
        implements MicroSmsConfigService {

    @Autowired
    private MicroSmsConfigDao microSmsConfigDao;

    @Override
    public DaoBase<MicroSmsConfig> getDao() {
        return microSmsConfigDao;
    }
}
