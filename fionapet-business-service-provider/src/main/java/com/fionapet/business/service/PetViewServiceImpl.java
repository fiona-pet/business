package com.fionapet.business.service;

import com.fionapet.business.entity.PetView;
import com.fionapet.business.repository.PetViewDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 宠物 Created by tom on 2016-07-19 10:31:06.
 */

@Service
public class PetViewServiceImpl extends CURDServiceBase<PetView> implements PetViewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetViewServiceImpl.class);
    @Autowired
    private PetViewDao petViewDao;


    @Override
    public DaoBase<PetView> getDao() {
        return petViewDao;
    }


}
