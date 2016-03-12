package com.lacronicus.outlookclone.calendar;

import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.util.ChronologyContextProvider;

/**
 * Created by fdoyle on 3/10/16.
 */
public class DayCellViewModel {
    public final int dayOfMonth;
    public final long startTimeStamp;
    public final long endTimeStamp;
    public final boolean isSelected;
    public final boolean hasAnyEvents;
    public final boolean isInSelectedMonth;
    public final OutlookDay calendarDay; // this may not exist
    public final boolean isToday;

    public DayCellViewModel( OutlookDay calendarDay, int dayOfMonth, long startTimeStamp, long endTimeStamp, boolean hasAnyEvents, boolean isInSelectedMonth, boolean isSelected, boolean isToday) {
        this.dayOfMonth = dayOfMonth;
        this.calendarDay = calendarDay;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.hasAnyEvents = hasAnyEvents;
        this.isInSelectedMonth = isInSelectedMonth;
        this.isSelected = isSelected;
        this.isToday = isToday;
    }

}
