package com.lacronicus.outlookclone.model;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by fdoyle on 3/8/16.
 */
public class OutlookYear {
    List<OutlookMonth> months; // dense list of months
    Calendar beginningOfYear;


    public OutlookYear(Calendar beginningOfYear) {
        this.beginningOfYear = (Calendar) beginningOfYear.clone();
        this.months = new ArrayList<>();
        // todo add months
        Calendar beginningOfMonthAtIndex = (Calendar) beginningOfYear.clone();
        for(int i = 1; i < 13; i ++) {
            beginningOfMonthAtIndex.set(Calendar.MONTH, i + 1);
            months.add(new OutlookMonth(this, beginningOfMonthAtIndex));
        }
    }

    public List<OutlookMonth> getMonths() {
        return months;
    }

    public void addEvent(Event eventToAdd) {

    }

    public void clearEvents() {
        for(OutlookMonth month : months) {
            month.clearEvents();
        }
    }

}
