package com.lacronicus.outlookclone.eventlist.viewmodel;

import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookEvent;

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
}
