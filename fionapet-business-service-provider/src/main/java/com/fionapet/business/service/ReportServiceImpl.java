package com.fionapet.business.service;

import com.fionapet.business.entity.InputMoneyRecord;
import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import com.fionapet.business.repository.*;
import org.apache.catalina.startup.Catalina;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.*;

/**
 *  报表统计
* Created by tom on 2016-07-18 11:56:10.
 */
public class ReportServiceImpl extends CURDServiceBase<ReportByPersonVO> implements ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ReportByItemDao reportByItemDao;
    @Autowired
    private GestPaidRecordDao gestPaidRecordDao;
    @Autowired
    private MedicRegisterRecordDao medicRegisterRecordDao;
    @Autowired
    private GestDao gestDao;
    @Autowired
    private InputMoneyRecordDao inputMoneyRecordDao;

    @Override
    public DaoBase<ReportByPersonVO> getDao() {
        return reportDao;
    }

    @Override
    public List<ReportByPersonVO> person(String month, String day) {
        return reportDao.findCreateDateBetween(getDate(month, day, "01"),getDate(month, day, "31"));
    }

    @Override
    public List<ReportByItemVO> item(String month, String day) {
        return reportByItemDao.findCreateDateBetween(getDate(month, day, "01"),getDate(month, day, "31"));
    }

    private String getDate(String month, String day, String defaultDay){
        if (month.length() == 1){
            month = "0" + month;
        }

        String date = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-") + month;

        if ("-".equals(day)){
            day = defaultDay;
        }else{
            if (day.length() == 1){
                day = "0" + day;
            }
        }

        return date + "-" + day;
    }

    @Override
    public List<String[]> gestPaidAction(String month, String day) {
        Date start = new Date();
        Date end = new Date();


        try {
            start = DateUtils.parseDate(getDate(month, day, "01") + " 00:00:00", "yyyy-MM-dd hh:mm:ss");
            end = getEndDate(month, day);
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!",e);

        }
        return gestPaidRecordDao.getReportForOperateAction(start, end);
    }

    private Date getEndDate(String month, String day){
        Date end = new Date();
        Calendar cale = Calendar.getInstance();

        cale.set(Calendar.MONTH, Integer.parseInt(month)-1);
        cale.add(Calendar.MONTH,1);
        cale.set(Calendar.DAY_OF_MONTH, 0);

        try {
            end = DateUtils.parseDate(getDate(month, day, cale.get(Calendar.DATE)+"") + " 23:59:59", "yyyy-MM-dd hh:mm:ss");
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!",e);
        }

        return end;
    }

    @Override
    public List<String[]> medicRegisterRecord(String month, String day) {
        Date start = new Date();
        Date end = new Date();

        try {
            start = DateUtils.parseDate(getDate(month, day, "01") + " 00:00:00", "yyyy-MM-dd hh:mm:ss");
            end = getEndDate(month, day);
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!",e);

        }
        return medicRegisterRecordDao.getByPaidStatusAndCreateDateBetweenGroupByDoctor("SM00051", start, end);
    }

    @Override
    public Map<String, Long> gestVip(String month, String day) {
        Date start = new Date();
        Date end = new Date();

        try {
            start = DateUtils.parseDate(getDate(month, day, "01") + " 00:00:00", "yyyy-MM-dd hh:mm:ss");
            end = getEndDate(month, day);
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!",e);

        }

        Long gestCount = gestDao.count();
        Long vipCount = gestDao.countByLevel(new String[]{"美容会员","医疗会员","美容和医疗会员"});

        Long vipMoneyTotal = gestDao.countVipMoneyByLevel(new String[]{"美容会员","医疗会员","美容和医疗会员"});

        Long inputMoneyTotal = ObjectUtils.defaultIfNull(inputMoneyRecordDao.countInputMoneyByCreateAndIsInput(start,end,1), 0l);
        Long outputMoneyTotal = ObjectUtils.defaultIfNull(inputMoneyRecordDao.countInputMoneyByCreateAndIsInput(start,end,0), 0l);


        Map<String, Long> result = new HashMap<String, Long>();
        result.put("gestCount", gestCount);
        result.put("vipCount", vipCount);
        result.put("vipMoneyTotal", vipMoneyTotal);
        result.put("inputMoneyTotal", inputMoneyTotal);
        result.put("outputMoneyTotal", outputMoneyTotal);

        return result;
    }


}
