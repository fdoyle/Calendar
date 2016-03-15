package com.lacronicus.outlookclone.api.model;

import com.google.gson.annotations.SerializedName;

public class OutlookEmailAddress {
    @SerializedName("Name")
    String name;
    @SerializedName("Address")
    String address;
}
