package com.lacronicus.outlookclone.model;

import com.lacronicus.outlookclone.api.model.Event;
import com.lacronicus.outlookclone.eventlist.viewmodel.AgendaViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

/**
 * OutlookCalendar represents the data displayed in the Monthly Calendar and Event list views.
 *
 * At any given time, it will contain three years worth of data: The year being looked at currently, the year before, and the year after. This will be updated as the user navigates.
 *
 */
public class OutlookCalendar {
    TreeMap<Integer, OutlookYear> calendarYears; //sparse map of years

    Calendar startOfMiddleYear;

    public OutlookCalendar(Calendar startOfMiddleYear) {
        this.startOfMiddleYear = (Calendar) startOfMiddleYear.clone();
        int middleYear = startOfMiddleYear.get(Calendar.YEAR);
        this.calendarYears = new TreeMap<>();
        this.calendarYears.put(middleYear, new OutlookYear(startOfMiddleYear));
        startOfMiddleYear.add(Calendar.YEAR, -1); // don't make a new one when creating an OutlookYear is going make its own anyway
        this.calendarYears.put(middleYear - 1, new OutlookYear(startOfMiddleYear));
        startOfMiddleYear.add(Calendar.YEAR, 2);
        this.calendarYears.put(middleYear + 1, new OutlookYear(startOfMiddleYear));
        startOfMiddleYear.add(Calendar.YEAR, -1); // reset its value back to true middle year
    }


    public TreeMap<Integer, OutlookYear> getYears() {
        return calendarYears;
    }

    /*
    * add events to this OutlookCalendar.
    *
    * Events that are not within the range of this calendar will be ignored
    * */
    public void addItemsToCalendar(List<Event> events) {
        for(Event event : events) {
            Calendar eventStartCalendar = Calendar.getInstance();
            eventStartCalendar.setTime(event.getDate());
            int year = eventStartCalendar.get(Calendar.YEAR);
            OutlookYear outlookYear =  calendarYears.get(year);

            if(outlookYear != null) {
                outlookYear.addEvent(event);
            }
        }
    }

    public void clearEvents() {
        for(OutlookYear year : calendarYears.values()) {
            year.clearEvents();
        }
    }
}
