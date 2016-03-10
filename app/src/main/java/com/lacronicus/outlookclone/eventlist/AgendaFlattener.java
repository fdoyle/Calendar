package com.lacronicus.outlookclone.eventlist;

import android.util.Pair;

import com.lacronicus.outlookclone.eventlist.viewmodel.AgendaViewModel;
import com.lacronicus.outlookclone.eventlist.viewmodel.DayHeaderViewModel;
import com.lacronicus.outlookclone.eventlist.viewmodel.EmptyDayViewModel;
import com.lacronicus.outlookclone.eventlist.viewmodel.EventViewModel;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookEvent;
import com.lacronicus.outlookclone.model.OutlookMonth;
import com.lacronicus.outlookclone.model.OutlookYear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flattens a Calendar tree for use in a Linear Recyclerview
 */
public class AgendaFlattener {

    public Pair<List<AgendaViewModel>, Map<OutlookDay, Integer>> flatten(OutlookCalendar calendar) {
        List<AgendaViewModel> items = new ArrayList<>();
        Map<OutlookDay, Integer> outlookDayToIndexMap = new HashMap<>();
        for (Integer year : calendar.getYears().navigableKeySet()) {
            flatten(items, outlookDayToIndexMap, calendar.getYears().get(year));
        }

        return new Pair<>(items, outlookDayToIndexMap);
    }

    public void flatten(List<AgendaViewModel> items, Map<OutlookDay, Integer> outlookDayToIndexMap, OutlookYear year) {
        //years are not represented in the list
        for (OutlookMonth month : year.getMonths()) {
            flatten(items, outlookDayToIndexMap, month);
        }
    }

    public void  flatten(List<AgendaViewModel> items, Map<OutlookDay, Integer> outlookDayToIndexMap, OutlookMonth month) {
        //months are not represented in the list
        for (OutlookDay day : month.getDays()) {
            flatten(items, outlookDayToIndexMap, day);
        }
    }

    public void flatten(List<AgendaViewModel> items, Map<OutlookDay, Integer> outlookDayToIndexMap, OutlookDay day) {
        items.add(new DayHeaderViewModel(day));
        outlookDayToIndexMap.put(day, items.size() - 1);

        if (day.getEvents().size() > 0) {
            for (OutlookEvent event : day.getEvents()) {
                flatten(items, event);
            }
        } else {
            AgendaViewModel itemToAdd = new EmptyDayViewModel(day);
            items.add(itemToAdd);
        }
    }

    public void flatten(List<AgendaViewModel> items, OutlookEvent event) {
        items.add(new EventViewModel(event));
    }

}
