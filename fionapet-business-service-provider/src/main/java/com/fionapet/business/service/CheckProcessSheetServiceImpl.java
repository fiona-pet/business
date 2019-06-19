package com.fionapet.business.service;

import com.fionapet.business.entity.CheckProcessSheet;
import com.fionapet.business.repository.CheckProcessSheetDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 检查处理单据 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class CheckProcessSheetServiceImpl extends CURDServiceBase<CheckProcessSheet>
        implements CheckProcessSheetService {

    @Autowired
    private CheckProcessSheetDao checkProcessSheetDao;

    @Override
    public DaoBase<CheckProcessSheet> getDao() {
        return checkProcessSheetDao;
    }
}
