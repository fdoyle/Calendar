package com.lacronicus.outlookclone.model;

import com.ibm.icu.impl.CalendarUtil;
import com.lacronicus.outlookclone.CalendarUtils;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by fdoyle on 3/9/16.
 */
@RunWith(RobolectricTestRunner.class)
public class OutlookCalendarTest {

    @Test
    public void test_that_correct_years_are_created_for_start_year() throws Exception {
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        calendar.set(Calendar.DAY_OF_YEAR, 1); //first day of year, not index 1
        calendar.set(Calendar.YEAR, 2000);
        OutlookCalendar outlookCalendar = new OutlookCalendar(calendar);
        Assert.assertEquals(3, outlookCalendar.getYears().keySet().size());
        Assert.assertTrue(outlookCalendar.getYears().keySet().containsAll(Arrays.asList(1999, 2000, 2001)));
    }

}