package com.fionapet.business.service;

import com.fionapet.business.entity.InHospitalRecordDetail;
import com.fionapet.business.entity.UserDictDetail;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.InHospitalRecordDetailDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  医院记录明细表
* Created by tom on 2016-07-25 09:32:31.
 */
public class InHospitalRecordDetailServiceImpl extends CURDServiceBase<InHospitalRecordDetail> implements InHospitalRecordDetailService {
    @Autowired
    private InHospitalRecordDetailDao inHospitalRecordDetailDao;

    @Autowired
    private UserDictDetailService userDictDetailService;

    @Override
    public DaoBase<InHospitalRecordDetail> getDao() {
        return inHospitalRecordDetailDao;
    }

    @Override
    public InHospitalRecordDetail createOrUpdte(InHospitalRecordDetail entity) {
        List<UserDictDetail> units = userDictDetailService.findByDictDetailCodeOrId(entity.getRecipeUnit());
        if (null != units && units.size()>0){
            entity.setRecipeUnit(units.get(0).getValueNameCn());
        }
        return super.createOrUpdte(entity);
    }
}


