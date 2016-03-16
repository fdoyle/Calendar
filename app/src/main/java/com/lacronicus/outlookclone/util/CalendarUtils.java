package com.lacronicus.outlookclone.util;

import java.util.Calendar;

/**
 * A set of simple util for common operations on the Calendar class.
 */
public class CalendarUtils {

    public static void pushToBeginningOfYear(Calendar calendar) {
        pushToBeginningOfDay(calendar);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
    }

    /**
    * sets the "time of day" fields to the start of the day
    * */
    public static void pushToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
        * sets the "time of day" fields to the end of the day
        * */
    public static void pushToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }
}
