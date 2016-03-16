package com.lacronicus.outlookclone.api.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Event {
    @SerializedName("@odata.id")
    public String oDataId;
    @SerializedName("@odata.etag")
    public String oDataETag;

    @SerializedName("Id")
    public String id;
    @SerializedName("Subject")
    public String subject;
    @SerializedName("Start")
    private OutlookDate start;
//    @SerializedName("End") //todo: this needs a custom deserializer, as it's sometimes an OutlookDate, and sometimes it's just a string.
//    public OutlookDate end;

    Calendar memoizedCalendar;

    public Date getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US); //always parse with US
        try {
            return format.parse(start.dateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e); //todo better error handling, ideally this would be handled at the parsing layer, rather than when used
        }
    }


    public Calendar getStartAsCalendar() {
        if(memoizedCalendar == null) {
            memoizedCalendar = Calendar.getInstance();
            memoizedCalendar.setTime(getDate());
        }
        return memoizedCalendar;
    }
}
