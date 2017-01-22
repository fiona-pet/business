package com.fionapet.business.service;

import com.fionapet.business.entity.CMSEntity;
import com.fionapet.business.entity.MedicMedictreatRecord;
import com.fionapet.business.entity.MedicRegisterRecord;
import com.fionapet.business.repository.MedicRegisterRecordDao;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.MedicMedictreatRecordDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;

/**
 *  医生处理记录
* Created by tom on 2016-07-18 11:56:10.
 */
public class MedicMedictreatRecordServiceImpl extends CURDServiceBase<MedicMedictreatRecord> implements MedicMedictreatRecordService {
    @Autowired
    private MedicMedictreatRecordDao medicMedictreatRecordDao;
    @Autowired
    private MedicRegisterRecordDao medicRegisterRecordDao;

    @Autowired
    private AppConfigService appConfigService;

    private static final String CURD_KEY = "CURD";

    @Override
    public DaoBase<MedicMedictreatRecord> getDao() {

        return medicMedictreatRecordDao;
    }

    @Override
    @Transactional
    public MedicMedictreatRecord createOrUpdte(MedicMedictreatRecord entity) {
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        synchronized (CURD_KEY) {
            MedicMedictreatRecord medicMedictreatRecord = medicMedictreatRecordDao.findByRegisterNo(entity.getRegisterNo());
            if (null == medicMedictreatRecord) {
                medicMedictreatRecord = new MedicMedictreatRecord();
                medicMedictreatRecord.setStatus(CMSEntity.DEFAULT());
                medicMedictreatRecord.setRegisterNo(entity.getRegisterNo());
                appConfigService.setCurrentUser(getCurrentUser());
                medicMedictreatRecord.setMediTreatmentCode(appConfigService.genNumberByName("就诊编号"));

                MedicRegisterRecord medicRegisterRecord = medicRegisterRecordDao.findByRegisterNo(entity.getRegisterNo());

                if (null != medicRegisterRecord) {
                    try {
                        BeanUtilsBean.getInstance().copyProperties(medicMedictreatRecord, medicRegisterRecord);
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
                    medicMedictreatRecord.setTemperature(entity.getTemperature());
                    medicMedictreatRecord.setBreath(entity.getBreath());
                    medicMedictreatRecord.setDoctorAdvice(entity.getDoctorAdvice());
                    medicMedictreatRecord.setDiagnosed(entity.getDiagnosed());
                    medicMedictreatRecord.setExamination(entity.getExamination());
                    medicMedictreatRecord.setBloodPressure(entity.getBloodPressure());
                    medicMedictreatRecord.setHeartbeat(entity.getHeartbeat());
                    medicMedictreatRecord.setRheme(entity.getRheme());

//                    try {
//                        BeanUtilsBean.getInstance().copyProperties(medicMedictreatRecord, entity);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
                    entity = medicMedictreatRecord;
                }
            }
        }
        return super.createOrUpdte(entity);
    }
}
