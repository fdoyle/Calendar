package com.lacronicus.outlookclone.calendar;

import com.lacronicus.outlookclone.model.OutlookDay;

/**
 * Callback object when a day is selected in a View
 */
public interface DaySelectedListener {
    void onDaySelected( OutlookDay day);
}