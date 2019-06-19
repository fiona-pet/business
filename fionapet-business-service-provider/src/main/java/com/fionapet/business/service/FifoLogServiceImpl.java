package com.fionapet.business.service;

import com.fionapet.business.entity.FifoLog;
import com.fionapet.business.repository.FifoLogDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 进库出库日志 Created by tom on 2016-07-25 09:32:31.
 */

@Service
public class FifoLogServiceImpl extends CURDServiceBase<FifoLog> implements FifoLogService {

    @Autowired
    private FifoLogDao fifoLogDao;

    @Override
    public DaoBase<FifoLog> getDao() {
        return fifoLogDao;
    }
}
