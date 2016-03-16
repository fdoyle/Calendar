package com.lacronicus.outlookclone.api;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.lacronicus.outlookclone.api.model.EventList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
* mock out the OutlookApi interface using json from the assets directory
* */
public class MockOutlookApi implements OutlookApi {
    Gson gson;
    AssetManager assetManager;

    public MockOutlookApi(AssetManager assetManager, Gson gson) {
        this.gson = gson;
        this.assetManager = assetManager;
    }

    @Override
    public EventList getEvents(String subject, String organizer, Date start, Date end) throws IOException {
        InputStream jsonStream = assetManager.open("outlookCalendar.json");
        return gson.fromJson(new BufferedReader(new InputStreamReader(jsonStream)), EventList.class);
    }
}
