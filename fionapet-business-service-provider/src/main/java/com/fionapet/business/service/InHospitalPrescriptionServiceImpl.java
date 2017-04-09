package com.fionapet.business.service;

import com.fionapet.business.entity.*;
import com.fionapet.business.repository.DictTypeDetailDao;
import com.fionapet.business.repository.InHospitalPrescriptionDetailDao;
import com.fionapet.business.repository.InHospitalRecordDao;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.dubbo.x.exception.ApiException;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.InHospitalPrescriptionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 *  住院处方
* Created by tom on 2016-07-18 15:37:44.
 */
public class InHospitalPrescriptionServiceImpl extends CURDServiceBase<InHospitalPrescription> implements InHospitalPrescriptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InHospitalPrescriptionServiceImpl.class);
    @Autowired
    private InHospitalPrescriptionDao inHospitalPrescriptionDao;
    @Autowired
    private InHospitalPrescriptionDetailDao inHospitalPrescriptionDetailDao;
    @Autowired
    private InHospitalRecordDao inHospitalRecordDao;
    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private DictTypeDetailDao dictTypeDetailDao;

    @Override
    public DaoBase<InHospitalPrescription> getDao() {
        return inHospitalPrescriptionDao;
    }

    @Override
    public InHospitalPrescription copy(String id, String inHospitalRecordCode) throws ApiException {
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        InHospitalRecord inHospitalRecord = inHospitalRecordDao.findByInHospitalNo(inHospitalRecordCode);

        if (null == inHospitalRecord){
            throw new ApiException("住院编号为空!");
        }

        InHospitalPrescription inHospitalPrescriptionOrgi = inHospitalPrescriptionDao.findByIdOrPrescriptionCode(id,id);
        InHospitalPrescription inHospitalPrescription = new InHospitalPrescription();
        if (null != inHospitalPrescriptionOrgi){
            //属性复制
            try {
                BeanUtilsBean.getInstance().copyProperties(inHospitalPrescription, inHospitalPrescriptionOrgi);
            } catch (Exception e) {
                LOGGER.warn("复制处方数据出错!", e);
                throw new ApiException("复制处方数据出错!");
            }
        }

        inHospitalPrescription.setId(null);
        inHospitalPrescription.setInHospitalId(inHospitalRecord.getId());
        inHospitalPrescription.setInHospitalNo(inHospitalRecordCode);

        //获取 病例编号
        String prescriptionCode = appConfigService.genNumberByName(AppConfigService.NUMBER_KEY_MEDIC_PRESCRIPTION_CODE);
        inHospitalPrescription.setPrescriptionCode(prescriptionCode);

        createOrUpdte(inHospitalPrescription);

        //处方明细 复制
        Set<InHospitalPrescriptionDetail> inHospitalPrescriptionDetailList = new HashSet<InHospitalPrescriptionDetail>();
        List<InHospitalPrescriptionDetail> inHospitalPrescriptionDetails = inHospitalPrescriptionDetailDao.findByPrescriptionId(inHospitalPrescriptionOrgi.getId());

        //状态
        DictTypeDetail status = dictTypeDetailDao.findByDictDetailCode("SM00001");

        for (InHospitalPrescriptionDetail inHospitalPrescriptionDetail: inHospitalPrescriptionDetails){
            InHospitalPrescriptionDetail inHospitalPrescriptionDetailDest = new InHospitalPrescriptionDetail();

            //属性复制
            try {
                BeanUtilsBean.getInstance().copyProperties(inHospitalPrescriptionDetailDest, inHospitalPrescriptionDetail);

            } catch (Exception e) {
                throw new ApiException("复制处方明细数据出错!");
            }

            inHospitalPrescriptionDetailDest.setId(null);
            inHospitalPrescriptionDetailDest.setPrescriptionId(inHospitalPrescription.getId());
            inHospitalPrescriptionDetailDest.setCreateDate(new Date());
            inHospitalPrescriptionDetailDest.setCreateUserId(getToken());
            inHospitalPrescriptionDetailDest.setUpdateDate(new Date());
            inHospitalPrescriptionDetailDest.setUpdateUserId(getToken());

            inHospitalPrescriptionDetailDest.setStatus(status);
            inHospitalPrescriptionDetailDest.setPaidStatus(null);

            inHospitalPrescriptionDetailList.add(inHospitalPrescriptionDetailDest);
        }

        inHospitalPrescriptionDetailDao.save(inHospitalPrescriptionDetailList);

        return inHospitalPrescription;
    }
}
