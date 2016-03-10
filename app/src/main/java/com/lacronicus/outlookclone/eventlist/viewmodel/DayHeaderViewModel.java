package com.lacronicus.outlookclone.eventlist.viewmodel;

import com.lacronicus.outlookclone.model.OutlookDay;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by fdoyle on 3/9/16.
 */
public class DayHeaderViewModel implements AgendaViewModel {
    OutlookDay day;

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public DayHeaderViewModel(OutlookDay day) {
        this.day = day;
    }

    public String getHeaderText() {
        return format.format(day.getStartOfDay());
    }

    @Override
    public OutlookDay getAssociatedDay() {
        return day;
    }
}
