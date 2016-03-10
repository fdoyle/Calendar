package com.lacronicus.outlookclone.model;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.Calendar;

/**
 * Created by fdoyle on 3/8/16.
 */
public class OutlookEvent {
    public final Calendar startTime;
    public final OutlookDay dayOfStart;

    public OutlookEvent(OutlookDay dayOfStart, Event event) {
        this.dayOfStart = dayOfStart;
        startTime = Calendar.getInstance();
        startTime.setTime(event.getDate());
    }
}
