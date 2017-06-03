package com.fionapet.business.service;

import com.fionapet.business.entity.DictTypeDetail;
import com.fionapet.business.entity.MedicMedictreatRecord;
import com.fionapet.business.entity.MedicPrescription;
import com.fionapet.business.entity.MedicPrescriptionDetail;
import com.fionapet.business.repository.*;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.dubbo.x.exception.ApiException;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *  医生处方明细
* Created by tom on 2016-07-18 11:56:08.
 */
public class MedicPrescriptionServiceImpl extends CURDServiceBase<MedicPrescription> implements MedicPrescriptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicPrescriptionServiceImpl.class);
    @Autowired
    private MedicPrescriptionDao medicPrescriptionDao;
    @Autowired
    private MedicMedictreatRecordDao medicMedictreatRecordDao;
    @Autowired
    private MedicPrescriptionDetailDao medicPrescriptionDetailDao;
    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;
    @Autowired
    private AppConfigService appConfigService;

    @Override
    public DaoBase<MedicPrescription> getDao() {
        return medicPrescriptionDao;
    }

    @Override
    @Transactional
    public MedicPrescription copy(String id, String medicRecordCode) throws ApiException {

        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        MedicMedictreatRecord medicMedictreatRecord = medicMedictreatRecordDao.findByMediTreatmentCode(medicRecordCode);

        if (null == medicMedictreatRecord){
            throw new ApiException("就诊编号为空!");
        }

        MedicPrescription medicPrescriptionOrgi = medicPrescriptionDao.findByIdOrPrescriptionCode(id,id);
        MedicPrescription medicPrescription = new MedicPrescription();
        if (null != medicPrescriptionOrgi){
            //属性复制
            try {
                BeanUtilsBean.getInstance().copyProperties(medicPrescription, medicPrescriptionOrgi);
            } catch (Exception e) {
                LOGGER.warn("复制处方数据出错!", e);
                throw new ApiException("复制处方数据出错!");
            }
        }

        medicPrescription.setId(null);
        medicPrescription.setMedicRecordId(medicMedictreatRecord.getId());
        medicPrescription.setMedicRecordCode(medicRecordCode);

        //获取 病例编号
        String prescriptionCode = appConfigService.genNumberByName(AppConfigService.NUMBER_KEY_MEDIC_PRESCRIPTION_CODE);
        medicPrescription.setPrescriptionCode(prescriptionCode);

        createOrUpdte(medicPrescription);

        //处方明细 复制
//        Set<MedicPrescriptionDetail> medicPrescriptionDetailList = new HashSet<MedicPrescriptionDetail>();
        Map<String, MedicPrescriptionDetail> medicPrescriptionDetailMap = new HashMap<String, MedicPrescriptionDetail>();
        List<MedicPrescriptionDetail> medicPrescriptionDetails = medicPrescriptionDetailDao.findByPrescriptionId(medicPrescriptionOrgi.getId());

        //状态
        DictTypeDetail status = dictTypeDetailDao.findByDictDetailCode("SM00001");

        for (MedicPrescriptionDetail medicPrescriptionDetail: medicPrescriptionDetails){
            MedicPrescriptionDetail medicPrescriptionDetailDest = new MedicPrescriptionDetail();

            //属性复制
            try {
                BeanUtilsBean.getInstance().copyProperties(medicPrescriptionDetailDest, medicPrescriptionDetail);

            } catch (Exception e) {
                throw new ApiException("复制处方明细数据出错!");
            }

            medicPrescriptionDetailDest.setId(null);
            medicPrescriptionDetailDest.setPrescriptionId(medicPrescription.getId());
            medicPrescriptionDetailDest.setCreateDate(new Date());
            medicPrescriptionDetailDest.setCreateUserId(getToken());
            medicPrescriptionDetailDest.setUpdateDate(new Date());
            medicPrescriptionDetailDest.setUpdateUserId(getToken());

            medicPrescriptionDetailDest.setStatus(status);
            medicPrescriptionDetailDest.setPaidStatus(null);

            medicPrescriptionDetailMap.put(medicPrescriptionDetailDest.getItemCode()+":" + medicPrescriptionDetailDest.getGroupName(), medicPrescriptionDetailDest);
//            medicPrescriptionDetailList.add(medicPrescriptionDetailDest);
        }

        medicPrescriptionDetailDao.save(medicPrescriptionDetailMap.values());

        return medicPrescription;
    }
}
