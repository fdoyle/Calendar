package com.lacronicus.outlookclone.model;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.Calendar;

/**
 * Model representing a single event in the OutlookCalendar
 */
public class OutlookEvent implements Comparable<OutlookEvent> {
    public final Calendar startTime;
    public final OutlookDay dayOfStart;
    String subject;
    String id;


    public OutlookEvent(OutlookDay dayOfStart, Event event) {
        this.dayOfStart = dayOfStart;
        startTime = Calendar.getInstance();
        startTime.setTime(event.getDate());
        this.subject = event.subject;
        this.id = event.id;
    }

    public OutlookEvent(Calendar startTime, String subject, String id) {
        this.dayOfStart = null;
        this.startTime = startTime;
        this.subject = subject;
        this.id = id;
    }

    @Override
    public int compareTo(OutlookEvent another) {
        int timeComparison = startTime.compareTo(another.startTime);
        if(timeComparison != 0) {
            return timeComparison;
        }
        int subjectComparison = subject.compareTo(another.subject);
        if(subjectComparison != 0) {
            return subjectComparison;
        }
        int idComparison = id.compareTo(another.id);
        if(idComparison != 0) {
            return idComparison;
        }
        return 0;
    }

    public String getSubject() {
        return subject;
    }
}
