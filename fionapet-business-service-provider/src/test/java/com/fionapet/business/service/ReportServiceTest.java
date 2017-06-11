package com.fionapet.business.service;

import org.junit.Test;

import java.util.Calendar;

/**
 * Created by tom on 2017/6/11.
 */
public class ReportServiceTest {

    @Test
    public void testGetLastDayOfMonth(){
        System.out.println(getLastDayOfMonth("5"));
    }

    private int getLastDayOfMonth(String month) {
        int m = Integer.parseInt(month);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, m-1);
        // 某年某月的最后一天
        return cal.getActualMaximum(Calendar.DATE);
    }
}
