package com.fionapet.business.service;

import com.fionapet.business.entity.FosterRecord;
import com.fionapet.business.entity.ReportByItemDayVO;
import com.fionapet.business.entity.ReportByItemMonthVO;
import com.fionapet.business.entity.ReportByItemVO;
import com.fionapet.business.entity.ReportByPersonVO;
import com.fionapet.business.entity.StatusEntity;
import com.fionapet.business.repository.FosterRecordDao;
import com.fionapet.business.repository.GestDao;
import com.fionapet.business.repository.GestPaidRecordDao;
import com.fionapet.business.repository.InputMoneyRecordDao;
import com.fionapet.business.repository.MedicRegisterRecordDao;
import com.fionapet.business.repository.ReportByItemDao;
import com.fionapet.business.repository.ReportByItemMonthDao;
import com.fionapet.business.repository.ReportDao;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dubbo.x.repository.DaoBase;
import org.dubbo.x.service.CURDServiceBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表统计 Created by tom on 2016-07-18 11:56:10.
 */

@Service
public class ReportServiceImpl extends CURDServiceBase<ReportByPersonVO> implements ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ReportByItemDao reportByItemDao;
    @Autowired
    private ReportByItemMonthDao reportByItemMonthDao;
    @Autowired
    private GestPaidRecordDao gestPaidRecordDao;
    @Autowired
    private MedicRegisterRecordDao medicRegisterRecordDao;
    @Autowired
    private GestDao gestDao;
    @Autowired
    private InputMoneyRecordDao inputMoneyRecordDao;
    @Autowired
    private FosterRecordDao fosterRecordDao;

    @Override
    public DaoBase<ReportByPersonVO> getDao() {
        return reportDao;
    }

    @Override
    public List<ReportByPersonVO> person(String year, String month, String day) {
        return reportDao.findCreateDateBetween(getDate(year, month, day, "01"),
                                               getDate(year, month, day, "31"));
    }

    @Override
    public List<ReportByItemDayVO> itemDay(String year, String month, String day) {
        if (!"-".equals(day)){
            return reportByItemDao.findByCreateDateOrderByTotalDesc(getDate(year, month, day, day));
        }else{
            return reportByItemDao.findCreateDateBetweenOrderByTotalDesc(getDate(year, month, day, "01"),
                                                         getDate(year, month, day, "31"));
        }

    }
    @Override
    public List<ReportByItemMonthVO> itemMonth(String year, String month) {
        if (month.length() == 1) {
            month = "0" + month;
        }
        return reportByItemMonthDao.findByCreateDateOrderByTotalDesc(year+"-"+month);
    }

    private String getDate(String year, String month, String day, String defaultDay) {
        if (month.length() == 1) {
            month = "0" + month;
        }

        String date = year;
        if (StringUtils.isEmpty(date)) {
            date = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-");
        } else {
            date = date + "-";
        }

        date += month;

        if ("-".equals(day)) {
            day = defaultDay;
        } else {
            if (day.length() == 1) {
                day = "0" + day;
            }
        }

        return date + "-" + day;
    }

    @Override
    public List<String[]> gestPaidAction(String year, String month, String day) {
        Date start = new Date();
        Date end = new Date();

        try {
            start =
                    DateUtils.parseDate(getDate(year, month, day, "01") + " 00:00:00",
                                        "yyyy-MM-dd hh:mm:ss");
            end = getEndDate(year, month, day);
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!", e);

        }
        return gestPaidRecordDao.getReportForOperateAction(start, end);
    }

    private Date getEndDate(String year, String month, String day) {
        Date end = new Date();
        Calendar cale = Calendar.getInstance();

        cale.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);

        try {
            end =
                    DateUtils.parseDate(
                            getDate(year, month, day, cale.get(Calendar.DATE) + "") + " 23:59:59",
                            "yyyy-MM-dd hh:mm:ss");
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!", e);
        }

        return end;
    }

    @Override
    public List<String[]> medicRegisterRecord(String year, String month, String day) {
        Date start = new Date();
        Date end = new Date();

        try {
            start =
                    DateUtils.parseDate(getDate(year, month, day, "01") + " 00:00:00",
                                        "yyyy-MM-dd hh:mm:ss");
            end = getEndDate(year, month, day);
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!", e);

        }
        return medicRegisterRecordDao
                .getByPaidStatusAndCreateDateBetweenGroupByDoctor("SM00051", start, end);
    }

    @Override
    public Map<String, Long> gestVip(String year, String month, String day) {
        Date start = new Date();
        Date end = new Date();

        try {
            start =
                    DateUtils.parseDate(getDate(year, month, day, "01") + " 00:00:00",
                                        "yyyy-MM-dd hh:mm:ss");
            end = getEndDate(year, month, day);
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!", e);

        }

        Long gestCount = gestDao.count();

        Long
                vipCount =
                gestDao.countByLevel(new String[]{"美容(1000)", "美容(2000)", "美容(5000)", "美容(1000)和医疗",
                                                  "美容(2000)和医疗", "美容(5000)和医疗"});

        Long
                vipMoneyTotal =
                gestDao.countVipMoneyByLevel(
                        new String[]{"美容(1000)", "美容(2000)", "美容(5000)", "美容(1000)和医疗",
                                     "美容(2000)和医疗", "美容(5000)和医疗"});

        Long
                inputMoneyTotal =
                ObjectUtils.defaultIfNull(
                        inputMoneyRecordDao.countInputMoneyByCreateAndIsInput(start, end, 1), 0l);
        Long
                outputMoneyTotal =
                ObjectUtils.defaultIfNull(
                        inputMoneyRecordDao.countInputMoneyByCreateAndIsInput(start, end, 0), 0l);

        Map<String, Long> result = new HashMap<String, Long>();
        result.put("gestCount", gestCount);
        result.put("vipCount", vipCount);
        result.put("vipMoneyTotal", vipMoneyTotal);
        result.put("inputMoneyTotal", inputMoneyTotal);
        result.put("outputMoneyTotal", outputMoneyTotal);

        return result;
    }

    @Override
    public Map<String, List> doctor(String year, String month, String user) {
        return doctorByType(year, month, user, "门诊处方");
    }

    @Override
    public Map<String, List> doctorInHospital(String year, String month, String user) {
        return doctorByType(year, month, user, "住院处方");
    }

    @Override
    public Map<String, String> foster(String year, String month, String day) {
        Date start = new Date();
        Date end = new Date();

        try {
            start =
                    DateUtils.parseDate(getDate(year, month, day, "01") + " 00:00:00",
                                        "yyyy-MM-dd hh:mm:ss");
            end = getEndDate(year, month, day);
        } catch (ParseException e) {
            LOGGER.warn("Data Format ERROR!", e);

        }

        Map<String, String> result = new HashMap<String, String>();

        List<FosterRecord>
                fosterRecords =
                fosterRecordDao.findByStatusDictDetailCodeAndCreateDateBetween(
                        StatusEntity.DEFAULT().getDictDetailCode(), start, end);

        result.put("fosterCount", fosterRecords.size() + "");
        Double total = 0d;
        for (FosterRecord fosterRecord : fosterRecords) {
            total += fosterRecord.getInputMoney();
        }
        result.put("fosterMoneyTotal", total + "");

        return result;
    }

    private Map<String, List> doctorByType(String year, String month, String user, String type) {
        int lastDay = getLastDayOfMonth(month);

        if (month.length() == 1) {
            month = "0" + month;
        }

        String date = year;
        if (StringUtils.isEmpty(date)) {
            date = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-");
        } else {
            date = date + "-";
        }

        date += month;

        LOGGER.debug("date:{} of lastDay:{}", date, lastDay);

        List<ReportByPersonVO>
                reportByPersonVOs =
                reportDao.findByNameAndCreateDateLikeAndType(user, date + "%", type);

        List<Double> totals = new ArrayList<Double>();
        boolean hasData = false;
        for (int i = 0; i < lastDay + 1; i++) {
            hasData = false;
            for (ReportByPersonVO reportByPersonVO : reportByPersonVOs) {
                if (reportByPersonVO.getCreateDate().equals(date + "-" + (i < 10 ? "0" + i : i))) {
                    totals.add(reportByPersonVO.getTotal());
                    hasData = true;
                }
            }

            if (!hasData) {
                totals.add(0d);
            }
        }

        Map<String, List> result = new HashMap<String, List>();

        result.put(user, totals);

        return result;
    }

    private int getLastDayOfMonth(String month) {
        int m = Integer.parseInt(month);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, m - 1);
        // 某年某月的最后一天
        return cal.getActualMaximum(Calendar.DATE);
    }


}
