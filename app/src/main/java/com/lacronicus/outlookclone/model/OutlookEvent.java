package com.lacronicus.outlookclone.model;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.Calendar;

/**
 * Created by fdoyle on 3/8/16.
 */
public class OutlookEvent implements Comparable<OutlookEvent> {
    public final Calendar startTime;
    public final OutlookDay dayOfStart;
    String title;


    public OutlookEvent(OutlookDay dayOfStart, Event event) {
        this.dayOfStart = dayOfStart;
        startTime = Calendar.getInstance();
        startTime.setTime(event.getDate());
        this.title = event.subject;
    }

    @Override
    public int compareTo(OutlookEvent another) {
        return startTime.compareTo(another.startTime);
    }

    public String getTitle() {
        return title;
    }
}
