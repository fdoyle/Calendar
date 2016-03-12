package com.lacronicus.outlookclone.eventlist.viewmodel;

import android.content.res.Resources;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.util.ChronologyContextProvider;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by fdoyle on 3/9/16.
 */
public class DayHeaderViewModel implements AgendaViewModel {
    OutlookDay day;
    ChronologyContextProvider chronologyContextProvider;

    SimpleDateFormat format = new SimpleDateFormat("cccc',' LLLL d", Locale.getDefault()); //ideally, this would be static, but according to http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html this dateformat is invalid, and so unit tests won't run

    public DayHeaderViewModel(ChronologyContextProvider chronologyContextProvider, OutlookDay day) {
        this.day = day;
        this.chronologyContextProvider = chronologyContextProvider;
    }

    public String getHeaderText(Resources resources) {
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
