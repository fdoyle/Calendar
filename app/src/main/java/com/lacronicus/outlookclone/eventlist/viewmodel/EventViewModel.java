package com.lacronicus.outlookclone.eventlist.viewmodel;

import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookEvent;

import java.text.SimpleDateFormat;

/**
 * Created by fdoyle on 3/9/16.
 */
public class EventViewModel implements AgendaViewModel {

    OutlookEvent event;

    public EventViewModel(OutlookEvent event) {
        this.event = event;
    }

    @Override
    public OutlookDay getAssociatedDay() {
        return event.dayOfStart;
    }

    public String getTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h':'mm a");
        return dateFormat.format(event.startTime.getTime());
    }

    public String getTitle() {
        return event.getSubject();
    }
}
