package com.lacronicus.outlookclone;

import java.util.Calendar;

/**
 * Created by fdoyle on 3/9/16.
 */
public class CalendarUtils {

    public static void pushToBeginningOfYear(Calendar calendar) {
        pushToBeginningOfDay(calendar);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
    }

    public static void pushToBeginningOfDay(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static void pushToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }
}
