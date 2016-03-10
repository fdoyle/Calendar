package com.lacronicus.outlookclone.eventlist.viewmodel;

import com.lacronicus.outlookclone.model.OutlookDay;

/**
 * Created by fdoyle on 3/9/16.
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
