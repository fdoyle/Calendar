package com.lacronicus.outlookclone.model;

import com.lacronicus.outlookclone.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * todo give this a better name
 *
 * Takes a Date, tells whether another Date is on the same day, the previous day, the day after
 *
 * inject this into objects to give them a sense of what "Today", "Yesterday", and "Tomorrow" mean
 * without having them rely directly on system clock
 *
 * todo unit test this
 */
public class RelativeTimeCalculator {
    Calendar beginningOfToday;
    Calendar endOfToday;

    public RelativeTimeCalculator(Date today) {
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

    public boolean isDateWithinToday(Date dateToCompare) {
        Calendar calendarToCompare = Calendar.getInstance();
        calendarToCompare.setTime(dateToCompare);
        return calendarToCompare.after(beginningOfToday) && calendarToCompare.before(endOfToday);
    }


    public RelativeTimeCalculator getTomorrow() {
        Calendar beginningOfTomorrow = (Calendar) beginningOfToday.clone();
        beginningOfTomorrow.add(Calendar.DAY_OF_YEAR, 1);
        return new RelativeTimeCalculator(beginningOfTomorrow.getTime());
    }

    public RelativeTimeCalculator getYesterday() {
        Calendar beginningOfYesterday = (Calendar) beginningOfToday.clone();
        beginningOfYesterday.add(Calendar.DAY_OF_YEAR, -1);
        return new RelativeTimeCalculator(beginningOfYesterday.getTime());

    }




}
