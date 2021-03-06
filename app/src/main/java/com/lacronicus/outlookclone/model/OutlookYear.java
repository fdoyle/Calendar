package com.lacronicus.outlookclone.model;

import android.util.Log;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Model representing a single year inthe OutlookCalendar
 */
public class OutlookYear {
    List<OutlookMonth> months; // dense list of months
    Calendar beginningOfYear;


    public OutlookYear(Calendar beginningOfYear) {
        this.beginningOfYear = (Calendar) beginningOfYear.clone(); //assume unsafe
        this.months = new ArrayList<>();
        Calendar beginningOfMonthAtIndex = (Calendar) this.beginningOfYear.clone();
        for(int i = 0; i < 12; i ++) {
            beginningOfMonthAtIndex.set(Calendar.MONTH, i);
            months.add(new OutlookMonth(this, (Calendar) beginningOfMonthAtIndex.clone()));
        }
    }

    public List<OutlookMonth> getMonths() {
        return months;
    }

    public void addEvent(Event eventToAdd) {
        months.get(eventToAdd.getStartAsCalendar().get(Calendar.MONTH) - Calendar.JANUARY).addEvent(eventToAdd);
    }

    public void clearEvents() {
        for(OutlookMonth month : months) {
            month.clearEvents();
        }
    }

}
