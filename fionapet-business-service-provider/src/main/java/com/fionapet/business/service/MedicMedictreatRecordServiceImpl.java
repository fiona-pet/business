package com.fionapet.business.service;

import com.fionapet.business.entity.CMSEntity;
import com.fionapet.business.entity.MedicMedictreatRecord;
import com.fionapet.business.entity.MedicRegisterRecord;
import com.fionapet.business.event.PetEvent;
import com.fionapet.business.repository.MedicMedictreatRecordDao;
import com.fionapet.business.repository.MedicRegisterRecordDao;
import com.fionapet.business.repository.PetDao;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 医生处理记录 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class MedicMedictreatRecordServiceImpl extends CURDServiceBase<MedicMedictreatRecord>
        implements MedicMedictreatRecordService, ApplicationEventPublisherAware {

    private final static Logger
            LOGGER =
            LoggerFactory.getLogger(MedicMedictreatRecordServiceImpl.class);

    @Autowired
    private MedicMedictreatRecordDao medicMedictreatRecordDao;
    @Autowired
    private MedicRegisterRecordDao medicRegisterRecordDao;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private PetDao petDao;

    private static final String CURD_KEY = "CURD";

    private ApplicationEventPublisher publisher;


    @Override
    public DaoBase<MedicMedictreatRecord> getDao() {

        return medicMedictreatRecordDao;
    }

    @Override
    @Transactional
    public MedicMedictreatRecord createOrUpdte(MedicMedictreatRecord entity) {
        LOGGER.debug("save entity:{}", entity);
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);

        synchronized (CURD_KEY) {
            MedicMedictreatRecord
                    medicMedictreatRecord =
                    medicMedictreatRecordDao.findByRegisterNo(entity.getRegisterNo());
            if (null == medicMedictreatRecord) {
                medicMedictreatRecord = new MedicMedictreatRecord();
                medicMedictreatRecord.setStatus(CMSEntity.DEFAULT());
                medicMedictreatRecord.setRegisterNo(entity.getRegisterNo());
                appConfigService.setCurrentUser(getCurrentUser());
                medicMedictreatRecord
                        .setMediTreatmentCode(appConfigService.genNumberByName("就诊编号"));

                MedicRegisterRecord
                        medicRegisterRecord =
                        medicRegisterRecordDao.findByRegisterNo(entity.getRegisterNo());

                if (null != medicRegisterRecord) {
                    try {
                        BeanUtilsBean.getInstance()
                                .copyProperties(medicMedictreatRecord, medicRegisterRecord);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    try {
                        BeanUtilsBean.getInstance().copyProperties(entity, medicMedictreatRecord);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (entity.getId() == null) {
                    entity = medicMedictreatRecord;
                } else {
                    entity.setId(medicMedictreatRecord.getId());
                }
            }

            publisher.publishEvent(new PetEvent(entity, entity.getPetId(), getToken()));

            return super.createOrUpdte(entity);
        }

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public MedicMedictreatRecord payReturnVisit(String uuid, String remark) {
        MedicMedictreatRecord medicMedictreatRecord = this.detail(uuid);
        if (null != medicMedictreatRecord && StringUtils.isNotBlank(remark) && StringUtils
                .isNotEmpty(remark)) {
            medicMedictreatRecord.setPayReturnVisit(true);
            medicMedictreatRecord.setPayReturnVisitDate(new Date());
            medicMedictreatRecord.setPayReturnVisitRemark(remark);
        }
        return this.createOrUpdte(medicMedictreatRecord);
    }
}
