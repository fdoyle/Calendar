package com.lacronicus.outlookclone.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fdoyle on 3/7/16.
 */
public class EventList {
    @SerializedName("@odata.context")
    public String context;
    @SerializedName("value")
    public List<Event> value;
}
