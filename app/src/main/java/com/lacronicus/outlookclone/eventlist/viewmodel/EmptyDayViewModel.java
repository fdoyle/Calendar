package com.lacronicus.outlookclone.eventlist.viewmodel;

import com.lacronicus.outlookclone.model.OutlookDay;

/**
 * ViewModel providing the data for an empty day entry in the Agenda View
 */
public class EmptyDayViewModel implements AgendaViewModel {
    OutlookDay day;

    public EmptyDayViewModel(OutlookDay day) {
        this.day = day;
    }

    @Override
    public OutlookDay getAssociatedDay() {
        return day;
    }
}
