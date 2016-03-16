package com.lacronicus.outlookclone.util;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fdoyle on 3/11/16.
 */
@RunWith(RobolectricTestRunner.class)
public class ChronologyContextProviderTest {

    @Test
    public void test_that_today_is_within_today() throws Exception {
        Date today = new Date();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertTrue(provider.isDateWithinToday(today));
    }

    @Test
    public void test_that_tomorrow_is_not_within_today() throws Exception {
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertFalse(provider.isDateWithinToday(tomorrow));
    }

    @Test
    public void test_that_tomorrow_is_within_tomorrow() throws Exception {
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertTrue(provider.getTomorrow().isDateWithinToday(tomorrow));
    }

    @Test
    public void test_that_yesterday_is_within_yesterday() throws Exception {
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date tomorrow = calendar.getTime();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertTrue(provider.getYesterday().isDateWithinToday(tomorrow));
    }

    @Test
    public void test_that_today_is_within_this_month() throws Exception{
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        Date today = calendar.getTime();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertTrue(provider.isDateWithinSameMonth(today));
    }

    @Test
    public void test_that_today_is_within_this_year() throws Exception{
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        Date today = calendar.getTime();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertTrue(provider.isDateWithinSameYear(today));
    }


    @Test
    public void test_that_next_month_is_not_within_this_month() throws Exception{
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        Date today = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Date nextMonth = calendar.getTime();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertFalse(provider.isDateWithinSameMonth(nextMonth));
    }

    @Test
    public void test_that_next_year_is_not_within_this_year() throws Exception{
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        Date today = calendar.getTime();
        calendar.add(Calendar.YEAR, 1);
        Date nextYear = calendar.getTime();
        ChronologyContextProvider provider = new ChronologyContextProvider(today);
        Assert.assertFalse(provider.isDateWithinSameMonth(nextYear));
    }
}