package com.lacronicus.outlookclone.eventlist.viewmodel;

import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookEvent;

import java.text.SimpleDateFormat;

/**
 * ViewModel providing the data for a single Event in the Agenda View
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
