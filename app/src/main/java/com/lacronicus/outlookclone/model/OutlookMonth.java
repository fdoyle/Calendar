package com.lacronicus.outlookclone.model;

import android.util.Log;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OutlookMonth {
    OutlookYear year;
    final Calendar startOfMonth;
    public final List<OutlookDay> days; //list of CalendarDays (dense)

    public OutlookMonth(OutlookYear year, Calendar startOfMonth, List<OutlookEvent> eventsInMonth) {
        this.year = year;
        this.startOfMonth = (Calendar) startOfMonth.clone(); //assume unsafe
        startOfMonth = null;
        days = new ArrayList<>();
        int daysInMonth = this.startOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar dayOfMonth = (Calendar) this.startOfMonth.clone();
        for(int i = 0; i != daysInMonth; i ++) {
            dayOfMonth.set(Calendar.DAY_OF_MONTH, i + 1);
            days.add(new OutlookDay(this, dayOfMonth));
        }
    }

    public OutlookMonth(OutlookYear year, Calendar startOfMonth) {
        this(year, startOfMonth, new ArrayList<OutlookEvent>());
    }

    public List<OutlookDay> getDays(){
        return days;
    }

    public void addEvent(Event eventToAdd) {
        days.get(eventToAdd.getStartAsCalendar().get(Calendar.DAY_OF_MONTH) - 1).addEvent(eventToAdd);
    }

    public Calendar getStartOfMonth() {
        return (Calendar) startOfMonth.clone();//don't let anyone change the original
    }

    public void clearEvents() {
        for(OutlookDay day : days) {
            day.clearEvents();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OutlookMonth that = (OutlookMonth) o;

        return startOfMonth.equals(that.startOfMonth);

    }

    @Override
    public int hashCode() {
        return startOfMonth.hashCode();
    }
}
