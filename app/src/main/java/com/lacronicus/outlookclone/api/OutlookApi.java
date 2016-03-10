package com.lacronicus.outlookclone.api;

import android.content.Context;

import com.lacronicus.outlookclone.api.model.EventList;

import java.io.IOException;
import java.util.Date;

/**
 * Created by fdoyle on 3/7/16.
 */
public interface OutlookApi {

    //GET https://outlook.office.com/api/v2.0/me/events?$select=Subject,Organizer,Start,End
    EventList getEvents(String subject, String organizer, Date start, Date end) throws IOException;
}
