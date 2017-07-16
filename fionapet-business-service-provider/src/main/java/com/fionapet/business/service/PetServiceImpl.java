package com.fionapet.business.service;

import com.fionapet.business.entity.Pet;
import com.fionapet.business.entity.PetView;
import com.fionapet.business.repository.PetViewDao;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.lang.reflect.InvocationTargetException;
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
    @Transactional
    public Pet createOrUpdte(Pet entity) {

        LOGGER.debug("create or update:{}", entity);

        if (null != entity.getPetBirthday()){
            entity.setAge(getAge(entity.getPetBirthday()));
        }

        if (null == entity.getId()){
            entity.setSickFileCode(appConfigService.genNumberByName(AppConfigService.NUMBER_KEY_BLBH));
        }

        if (null != entity.getId()) {
            Pet pet = getDao().findOne(entity.getId());

            try {
                ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                BeanUtilsBean.getInstance().copyProperties(pet, entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return super.createOrUpdte(pet);
        }

        return super.createOrUpdte(entity);
    }

    //由出生日期获得年龄
    public float getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);

        int age = yearNow - yearBirth;

        LOGGER.debug("yearNow:{}, yearBirth:{}", yearNow, yearBirth);
        LOGGER.debug("month:{}",(Math.abs(monthBirth-monthNow)/12.0));

        return (float)(age + Math.abs(monthBirth-monthNow)/12.0);
    }

}
