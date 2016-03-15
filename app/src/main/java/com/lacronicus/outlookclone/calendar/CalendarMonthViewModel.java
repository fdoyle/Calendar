package com.lacronicus.outlookclone.calendar;

import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookMonth;

/**
 * ViewModel providing data for a Calendar Month view
 */
public class CalendarMonthViewModel {
    public final OutlookMonth outlookMonth;
    public OutlookDay selectedDay;

    public CalendarMonthViewModel(OutlookMonth outlookMonth) {
        this.outlookMonth = outlookMonth;
    }
}
