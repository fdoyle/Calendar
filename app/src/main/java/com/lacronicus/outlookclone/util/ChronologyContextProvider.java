package com.lacronicus.outlookclone.util;

import java.util.Calendar;
import java.util.Date;

/**
 * <p/>
 * Takes a Date, tells whether another Date is on the same day, the previous day, the day after
 * <p/>
 * inject this into objects to give them a sense of what "Today", "Yesterday", and "Tomorrow" mean
 * without having them rely directly on system clock
 * <p/>
 */
public class ChronologyContextProvider {
    Calendar beginningOfToday;
    Calendar endOfToday;

    private ChronologyContextProvider cachedTomorrow;
    private ChronologyContextProvider cachedYesterday;

    Calendar calendarToCompare = Calendar.getInstance(); //don't make a new Calendar for every comparison, just reuse this one

    public ChronologyContextProvider(Date today) {
        beginningOfToday = Calendar.getInstance();
        beginningOfToday.setTime(today);
        CalendarUtils.pushToBeginningOfDay(beginningOfToday);

        endOfToday = Calendar.getInstance();
        endOfToday.setTime(today);
        CalendarUtils.pushToEndOfDay(endOfToday);
    }


    public boolean isDateBeforeToday(Date dateToCompare) {
        calendarToCompare = Calendar.getInstance();
        calendarToCompare.setTime(dateToCompare);
        return calendarToCompare.before(beginningOfToday);
    }

    public boolean isDateAfterToday(Date dateToCompare) {
        calendarToCompare.setTime(dateToCompare);
        return calendarToCompare.after(endOfToday);
    }

    public boolean isDateWithinToday(Date dateToCheck) {
        calendarToCompare.setTime(dateToCheck);
        return calendarToCompare.equals(beginningOfToday) || calendarToCompare.after(beginningOfToday) && calendarToCompare.before(endOfToday);
    }

    public boolean isDateWithinSameMonth(Date dateToCheck) {
        calendarToCompare.setTime(dateToCheck);
        return beginningOfToday.get(Calendar.MONTH) == calendarToCompare.get(Calendar.MONTH) && beginningOfToday.get(Calendar.YEAR) == calendarToCompare.get(Calendar.YEAR);
    }

    public boolean isDateWithinSameYear(Date dateToCheck) {
        calendarToCompare.setTime(dateToCheck);
        return beginningOfToday.get(Calendar.YEAR) == calendarToCompare.get(Calendar.YEAR);
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
