package com.fionapet.business.service;

import com.fionapet.business.entity.MedicVaccine;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dubbo.x.entity.PageSearch;
import org.dubbo.x.entity.SearchFilter;
import org.dubbo.x.entity.Sort;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import com.fionapet.business.repository.MedicVaccineDao;
import org.jboss.resteasy.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Calendar;
import java.util.Date;

/**
 *  育苗表
* Created by tom on 2016-07-18 11:56:09.
 */
public class MedicVaccineServiceImpl extends CURDServiceBase<MedicVaccine> implements MedicVaccineService {
    @Autowired
    private MedicVaccineDao medicVaccineDao;

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
        date.setDate(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-2);

        filter.setValue(date);

        pageSearch.getAndFilters().add(filter);

        return super.page(pageSearch);
    }
}
