package com.lacronicus.outlookclone;

import com.lacronicus.outlookclone.api.model.Event;

import java.util.Date;
import java.util.Random;

/**
 * Created by fdoyle on 3/8/16.
 */
public class MockDataFactory {


    public Event getFakeEvent() {
        Random random = new Random();
        Event event = new Event();
        event.id = "foo";
        event.subject = "event " + random.nextInt();
        return event;
    }
}
