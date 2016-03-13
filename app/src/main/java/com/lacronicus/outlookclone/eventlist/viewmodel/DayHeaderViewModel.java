package com.lacronicus.outlookclone.eventlist.viewmodel;

import android.content.res.Resources;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.util.ChronologyContextProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by fdoyle on 3/9/16.
 */
public class DayHeaderViewModel implements AgendaViewModel {
    OutlookDay day;
    ChronologyContextProvider chronologyContextProvider;


    public DayHeaderViewModel(ChronologyContextProvider chronologyContextProvider, OutlookDay day) {
        this.day = day;
        this.chronologyContextProvider = chronologyContextProvider;
    }

    public String getHeaderText(Resources resources, DateFormat format) {
        String prefix;
        if(chronologyContextProvider.isDateWithinToday(day.getStartOfDay())) {
            prefix = resources.getString(R.string.header_text_prefix_today);
        } else if(chronologyContextProvider.getTomorrow().isDateWithinToday(day.getStartOfDay())) {
            prefix = resources.getString(R.string.header_text_prefix_tomorrow);
        } else if(chronologyContextProvider.getYesterday().isDateWithinToday(day.getStartOfDay())) {
            prefix = resources.getString(R.string.header_text_prefix_yesterday);
        } else {
            prefix = "";
        }
        return prefix + format.format(day.getStartOfDay());
    }

    @Override
    public OutlookDay getAssociatedDay() {
        return day;
    }

    public boolean isToday() {
        return chronologyContextProvider.isDateWithinToday(day.getStartOfDay());
    }
}
