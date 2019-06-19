package com.fionapet.business.service;

import com.fionapet.business.entity.Gest;
import com.fionapet.business.entity.MedicVaccine;
import com.fionapet.business.repository.GestDao;
import com.fionapet.business.repository.MedicVaccineDao;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.entity.SearchFilter;
import org.dubbo.x.entity.Sort;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 育苗表 Created by tom on 2016-07-18 11:56:09.
 */

@Service
public class MedicVaccineServiceImpl extends CURDServiceBase<MedicVaccine>
        implements MedicVaccineService {

    @Autowired
    private MedicVaccineDao medicVaccineDao;
    @Autowired
    private GestDao gestDao;

    @Override
    public DaoBase<MedicVaccine> getDao() {
        return medicVaccineDao;
    }

    @Override
    public Page<MedicVaccine> page(PageSearch pageSearch) {
        Sort sort = new Sort();
        sort.setFieldName("estimateTime");
        sort.setDirection("asc");
        pageSearch.setSort(sort);

        SearchFilter filter = new SearchFilter();
        filter.setFieldName("estimateTime");
        filter.setOperator("GTE");

        Date date = new Date();
        date.setDate(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 2);

        filter.setValue(date);

        pageSearch.getAndFilters().add(filter);

        Page<MedicVaccine> page = super.page(pageSearch);

        for (MedicVaccine medicVaccine : page.getContent()) {
            Gest gest = gestDao.findOne(medicVaccine.getGestId());
            if (null != gest) {
                medicVaccine.setGestName(
                        medicVaccine.getGestName() + "[" + gest.getMobilePhone() + "]");
            }
        }

        return page;
    }
}
