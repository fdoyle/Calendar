package com.lacronicus.outlookclone.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

/**
 * Created by fdoyle on 3/15/16.
 */

@RunWith(RobolectricTestRunner.class)
public class OutlookEventTest {

    Calendar earlierCalendar;
    Calendar laterCalendar;

    String earlierSubject;
    String laterSubject;

    String earlierId;
    String laterId;


    @Before
    public void setUp() throws Exception {
        earlierCalendar = Calendar.getInstance();
        laterCalendar = Calendar.getInstance();
        laterCalendar.add(Calendar.DAY_OF_MONTH, 1);
        earlierSubject = "a";
        laterSubject = "b";
        earlierId = "a";
        laterId = "b";

    }

    @Test
    public void test_that_items_are_sorted_by_date_first() throws Exception {
        OutlookEvent firstEvent = new OutlookEvent(earlierCalendar, laterSubject, laterId);
        OutlookEvent secondEvent = new OutlookEvent(laterCalendar, earlierSubject, earlierId);
        Assert.assertTrue(firstEvent.compareTo(secondEvent) < 0);
        Assert.assertTrue(secondEvent.compareTo(firstEvent) > 0);
    }

    @Test
    public void test_that_items_are_sorted_by_title_when_date_is_the_same() throws Exception {
        OutlookEvent firstEvent = new OutlookEvent(earlierCalendar, earlierSubject, laterId);
        OutlookEvent secondEvent = new OutlookEvent(earlierCalendar, laterSubject, earlierId);
        Assert.assertTrue(firstEvent.compareTo(secondEvent) < 0);
        Assert.assertTrue(secondEvent.compareTo(firstEvent) > 0);
    }

    @Test
    public void test_that_items_are_sorted_by_id_when_date_and_title_are_the_same() throws Exception {
        OutlookEvent firstEvent = new OutlookEvent(earlierCalendar, earlierSubject, earlierId);
        OutlookEvent secondEvent = new OutlookEvent(earlierCalendar, earlierSubject, laterId);
        Assert.assertTrue(firstEvent.compareTo(secondEvent) < 0);
        Assert.assertTrue(secondEvent.compareTo(firstEvent) > 0);
    }

    @Test
    public void test_that_items_with_equal_start_subject_id_are_equal() throws Exception {
        OutlookEvent firstEvent = new OutlookEvent(earlierCalendar, earlierSubject, earlierId);
        OutlookEvent secondEvent = new OutlookEvent(earlierCalendar, earlierSubject, earlierId);
        Assert.assertTrue(firstEvent.compareTo(secondEvent) == 0);
        Assert.assertTrue(secondEvent.compareTo(firstEvent) == 0);
    }


}