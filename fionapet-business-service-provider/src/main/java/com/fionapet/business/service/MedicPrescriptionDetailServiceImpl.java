package com.fionapet.business.service;

import com.fionapet.business.entity.MedicPrescriptionDetail;
import com.fionapet.business.entity.UserDict;
import com.fionapet.business.entity.UserDictDetail;
import com.fionapet.business.repository.UserDictDao;
import com.fionapet.business.repository.UserDictDetailDao;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.entity.Sort;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.MedicPrescriptionDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *  医生处方明细
* Created by tom on 2016-07-18 11:56:10.
 */
public class MedicPrescriptionDetailServiceImpl extends CURDServiceBase<MedicPrescriptionDetail> implements MedicPrescriptionDetailService {
    @Autowired
    private MedicPrescriptionDetailDao medicPrescriptionDetailDao;
    @Autowired
    private UserDictDetailService userDictDetailService;

    @Override
    public DaoBase<MedicPrescriptionDetail> getDao() {
        return medicPrescriptionDetailDao;
    }

    @Override
    public Page<MedicPrescriptionDetail> page(PageSearch pageSearch) {
        if (null == pageSearch.getSort()) {
            Sort sort = new Sort();
            sort.setFieldName("groupName,createDate");
            sort.setDirection("asc");
            pageSearch.setSort(sort);
        }

        return super.page(pageSearch);
    }

    @Override
    public MedicPrescriptionDetail createOrUpdte(MedicPrescriptionDetail entity) {

        List<UserDictDetail> units = userDictDetailService.findByDictDetailCodeOrId(entity.getRecipeUnit());
        if (null != units && units.size()>0){
            entity.setRecipeUnit(units.get(0).getValueNameCn());
        }
        return super.createOrUpdte(entity);
    }
}
