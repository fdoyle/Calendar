package com.lacronicus.outlookclone;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.lacronicus.outlookclone.api.MockOutlookApi;
import com.lacronicus.outlookclone.api.OutlookApi;
import com.lacronicus.outlookclone.api.model.EventList;
import com.lacronicus.outlookclone.calendar.CalendarView;
import com.lacronicus.outlookclone.calendar.DaySelectedListener;
import com.lacronicus.outlookclone.eventlist.AgendaView;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.model.OutlookDay;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DaySelectedListener {

    OutlookApi api;

    OutlookCalendar outlookCalendar;

    CalendarView calendarView;
    AgendaView agendaView;

    MainActivityOnScrollListener scrollListener = new MainActivityOnScrollListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();

        Calendar beginningOfYear = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfYear(beginningOfYear);

        outlookCalendar = new OutlookCalendar(Calendar.getInstance());

        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        OutlookCalendar outlookCalendar = new OutlookCalendar(beginningOfYear);

        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setContent(outlookCalendar);

        agendaView = (AgendaView) findViewById(R.id.agenda_view);
        agendaView.setContent(outlookCalendar);

        agendaView.setOnScrollListener(scrollListener);
        calendarView.setDaySelectedListener(this);
    }

    public class MainActivityOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                OutlookDay topmostDay = agendaView.getDayForTopmostView();
                calendarView.setSelectedDay(topmostDay);
        }
    }

    @Override
    public void onDaySelected(OutlookDay day) {
        agendaView.setOnScrollListener(null);
        agendaView.setSelectedDay(day);
        agendaView.setOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_month, menu);
        menu.findItem(R.id.action_toggle_calendar).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                toggleCalendar();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void toggleCalendar() {
        calendarView.setVisibility(calendarView.getVisibility() == View.VISIBLE ? View.GONE :View.VISIBLE);
    }

    public void loadData() {
        api = new MockOutlookApi(getResources().getAssets(), new Gson()); //todo: inject with dagger

        try {
            EventList events = api.getEvents(null, null, null, null);
        } catch (IOException ioException) {
            setErrorState();
        }
    }

    public void setErrorState() {

    }
}
