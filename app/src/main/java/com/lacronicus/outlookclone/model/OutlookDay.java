package com.lacronicus.outlookclone.model;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by fdoyle on 3/8/16.
 */
public class OutlookDay {
    final Calendar startOfDay;
    SortedSet<OutlookEvent> events;
    Date startOfDayDate;
    public final OutlookMonth month;

    public OutlookDay(OutlookMonth month, Calendar startOfDay, List<OutlookEvent> events) {
        this.month = month;
        this.startOfDay = (Calendar) startOfDay.clone();
        this.events = new TreeSet<>();
        this.events.addAll(events);
        this.startOfDayDate = startOfDay.getTime();
    }

    /**
     * Do not modify this Date
     *
     * (ideally, we'd just make a clone so it can't be modified, but since this will be called
     * while scrolling to get day header text, we'd be making a ton of objects)
     */
    public Date getStartOfDay() {
        return startOfDayDate;
    }

    public OutlookDay(OutlookMonth month, Calendar startOfDay) {
        this(month, startOfDay, new ArrayList<OutlookEvent>());
    }

    public void addEvent(Event event) {
        OutlookEvent outlookEvent = new OutlookEvent(this, event);
        this.events.add(outlookEvent);
    }

    public SortedSet<OutlookEvent> getEvents() {
        return events;
    }

    public boolean hasAnyEvents() {
        return events.size() > 0;
    }

    public void clearEvents() {
        events.clear();
    }

    public int getDayOfMonth() {
        return startOfDay.get(Calendar.DAY_OF_MONTH);
    }
}
