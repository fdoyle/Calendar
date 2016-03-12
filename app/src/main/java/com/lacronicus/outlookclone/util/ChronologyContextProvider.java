package com.lacronicus.outlookclone.util;

import com.lacronicus.outlookclone.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * todo give this a better name
 * <p/>
 * Takes a Date, tells whether another Date is on the same day, the previous day, the day after
 * <p/>
 * inject this into objects to give them a sense of what "Today", "Yesterday", and "Tomorrow" mean
 * without having them rely directly on system clock
 * <p/>
 * todo unit test this
 */
public class ChronologyContextProvider {
    Calendar beginningOfToday;
    Calendar endOfToday;

    private ChronologyContextProvider cachedTomorrow;
    private ChronologyContextProvider cachedYesterday;

    public ChronologyContextProvider(Date today) {
        beginningOfToday = Calendar.getInstance();
        beginningOfToday.setTime(today);
        CalendarUtils.pushToBeginningOfDay(beginningOfToday);

        endOfToday = Calendar.getInstance();
        endOfToday.setTime(today);
        CalendarUtils.pushToEndOfDay(endOfToday);
    }


    public boolean isDateBeforeToday(Date dateToCompare) {
        Calendar calendarToCompare = Calendar.getInstance();
        calendarToCompare.setTime(dateToCompare);
        return calendarToCompare.before(beginningOfToday);
    }

    public boolean isDateAfterToday(Date dateToCompare) {
        Calendar calendarToCompare = Calendar.getInstance();
        calendarToCompare.setTime(dateToCompare);
        return calendarToCompare.after(endOfToday);
    }

    public boolean isDateWithinToday(Date dateToCheck) {
        Calendar calendarToCheck = Calendar.getInstance();
        calendarToCheck.setTime(dateToCheck);
        return calendarToCheck.equals(beginningOfToday) || calendarToCheck.after(beginningOfToday) && calendarToCheck.before(endOfToday);
    }


    public ChronologyContextProvider getTomorrow() {
        if (cachedTomorrow == null) {
            Calendar beginningOfTomorrow = (Calendar) beginningOfToday.clone();
            beginningOfTomorrow.add(Calendar.DAY_OF_YEAR, 1);
            cachedTomorrow = new ChronologyContextProvider(beginningOfTomorrow.getTime());
        }
        return cachedTomorrow;
    }

    public ChronologyContextProvider getYesterday() {
        if (cachedYesterday == null) {
            Calendar beginningOfYesterday = (Calendar) beginningOfToday.clone();
            beginningOfYesterday.add(Calendar.DAY_OF_YEAR, -1);
            cachedYesterday = new ChronologyContextProvider(beginningOfYesterday.getTime());
        }
        return cachedYesterday;

    }


}
