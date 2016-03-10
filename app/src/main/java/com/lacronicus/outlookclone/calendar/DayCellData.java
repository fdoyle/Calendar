package com.lacronicus.outlookclone.calendar;

import com.lacronicus.outlookclone.model.OutlookDay;

/**
 * Created by fdoyle on 3/10/16.
 */
public class DayCellData {
    public int dayOfMonth;
    public final long startTimeStamp;
    public final long endTimeStamp;
    boolean isSelected;
    boolean hasAnyEvents;
    boolean isInSelectedMonth;
    OutlookDay calendarDay; // this may not exist

    public DayCellData(OutlookDay calendarDay, int dayOfMonth, long startTimeStamp, long endTimeStamp, boolean hasAnyEvents, boolean isInSelectedMonth, boolean isSelected) {
        this.dayOfMonth = dayOfMonth;
        this.calendarDay = calendarDay;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.hasAnyEvents = hasAnyEvents;
        this.isInSelectedMonth = isInSelectedMonth;
        this.isSelected = isSelected;
    }
}
