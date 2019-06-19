package com.fionapet.business.service;

import com.fionapet.business.entity.PetSmallRace;
import com.fionapet.business.repository.PetSmallRaceDao;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 宠物品种 Created by tom on 2016-07-19 13:15:47.
 */

@Service
public class PetSmallRaceServiceImpl extends CURDServiceBase<PetSmallRace>
        implements PetSmallRaceService {

    @Autowired
    private PetSmallRaceDao petSmallRaceDao;

    @Override
    public DaoBase<PetSmallRace> getDao() {
        return petSmallRaceDao;
    }
}
