package com.lacronicus.outlookclone.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fdoyle on 3/7/16.
 */
public class OutlookDate {
    @SerializedName("DateTime")
    public String dateTime;
    @SerializedName("TimeZone")
    public String timeZone; //todo: request conformity with ISO 8601 on serverside to eliminate this field

}
