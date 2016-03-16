package com.lacronicus.outlookclone.calendar;

import com.lacronicus.outlookclone.eventlist.viewmodel.AgendaViewModel;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.model.OutlookMonth;
import com.lacronicus.outlookclone.model.OutlookYear;

import java.util.ArrayList;
import java.util.List;

/**
 * Flattens an OutlookCalendar for use in a CalendarView
 */
public class MonthsFlattener {

    public List<CalendarMonthViewModel> flatten(OutlookCalendar calendar) {
        List<CalendarMonthViewModel> items = new ArrayList<>();
        for(Integer year : calendar.getYears().navigableKeySet()) {
            items.addAll(flatten(calendar.getYears().get(year)));
        }
        return items;
    }

    public List<CalendarMonthViewModel> flatten(OutlookYear year) {
        List<CalendarMonthViewModel> items = new ArrayList<>();
        //years are not represented in the list
        for(OutlookMonth month : year.getMonths()) {
            items.add(new CalendarMonthViewModel(month));
        }
        return items;
    }
}
