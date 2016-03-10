package com.lacronicus.outlookclone.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fdoyle on 3/7/16.
 */
public class OutlookEmailAddress {
    @SerializedName("Name")
    String name;
    @SerializedName("Address")
    String address;
}
