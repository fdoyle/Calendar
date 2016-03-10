package com.lacronicus.outlookclone.calendar;

import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookMonth;

/**
 * Created by fdoyle on 3/9/16.
 */
public class CalendarMonthViewModel {
    public final OutlookMonth outlookMonth;
    public OutlookDay selectedDay;

    public CalendarMonthViewModel(OutlookMonth outlookMonth) {
        this.outlookMonth = outlookMonth;
    }
}
