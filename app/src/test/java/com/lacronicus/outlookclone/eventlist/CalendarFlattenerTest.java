package com.lacronicus.outlookclone.eventlist;

import com.lacronicus.outlookclone.util.CalendarUtils;
import com.lacronicus.outlookclone.eventlist.viewmodel.AgendaViewModel;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.util.ChronologyContextProvider;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fdoyle on 3/9/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class CalendarFlattenerTest {

    //this test isn't pure, but the real one, once proven, makes an easier "mock" to work with than an actual mock
    //fun fact: this test actually caught a bug!
    @Test
    public void test_that_day_headers_and_no_events_items_are_added_when_no_events_exist() throws Exception {
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfDay(calendar);
        calendar.set(Calendar.DAY_OF_YEAR, 1); //first day of year, not index 1
        calendar.set(Calendar.YEAR, 2000);
        OutlookCalendar outlookCalendar = new OutlookCalendar(calendar);
        List<AgendaViewModel> viewModels = new AgendaFlattener(new ChronologyContextProvider(new Date())).flatten(outlookCalendar).first;
        final int DAYS_IN_1999_2000_2001 = 365 * 3 + 1;
        Assert.assertEquals(2 * DAYS_IN_1999_2000_2001,viewModels.size());
    }
}