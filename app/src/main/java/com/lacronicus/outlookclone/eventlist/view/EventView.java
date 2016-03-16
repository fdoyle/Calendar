package com.lacronicus.outlookclone.eventlist.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.eventlist.viewmodel.EventViewModel;

/**
 * View representing a single event in the Agenda View
 */
public class EventView extends FrameLayout {

    TextView timeText;
    TextView titleText;

    public EventView(Context context) {
        super(context);
        init();
    }

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EventView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_agenda_event, this);
        timeText = (TextView) findViewById(R.id.agenda_event_time);
        titleText = (TextView) findViewById(R.id.agenda_event_title);
    }

    public void setContent(EventViewModel viewModel){
        timeText.setText(viewModel.getTimeString());
        titleText.setText(viewModel.getTitle());
    }
}
