package com.fionapet.business.service;

import com.fionapet.business.entity.Pet;
import com.fionapet.business.entity.PetView;
import com.fionapet.business.repository.PetViewDao;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.PetDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;


/**
 *  宠物
* Created by tom on 2016-07-19 10:31:06.
 */
public class PetServiceImpl extends CURDServiceBase<Pet> implements PetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PetServiceImpl.class);
    @Autowired
    private PetDao petDao;
    @Autowired
    private AppConfigService appConfigService;

    @Override
    public DaoBase<Pet> getDao() {
        return petDao;
    }

    @Override
    public Pet detail(String uuid) {
        Pet pet = super.detail(uuid);

        if (null != pet.getPetBirthday()) {
            pet.setAge(Math.abs(DateUtils.truncatedCompareTo(new Date(), pet.getPetBirthday(), Calendar.YEAR)));
            petDao.save(pet);
        }

        return super.detail(uuid);
    }

    @Override
    public Pet createOrUpdte(Pet entity) {
        if (null == entity.getId()){
            entity.setSickFileCode(appConfigService.genNumberByName(AppConfigService.NUMBER_KEY_BLBH));
        }

        if (null != entity.getPetBirthday()){
            entity.setAge(Math.abs(DateUtils.truncatedCompareTo(new Date(), entity.getPetBirthday(), Calendar.YEAR)));
        }

        return super.createOrUpdte(entity);
    }

}
