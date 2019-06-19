package com.fionapet.business.service;

import com.fionapet.business.entity.PetRace;
import com.fionapet.business.repository.PetRaceDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 宠物种类 Created by tom on 2016-07-31 16:42:52.
 */

@Service
public class PetRaceServiceImpl extends CURDServiceBase<PetRace> implements PetRaceService {

    @Autowired
    private PetRaceDao petRaceDao;

    @Override
    public DaoBase<PetRace> getDao() {
        return petRaceDao;
    }
}
