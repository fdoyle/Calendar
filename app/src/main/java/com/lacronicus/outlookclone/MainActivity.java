package com.lacronicus.outlookclone;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
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
import com.lacronicus.outlookclone.util.ChronologyContextProvider;
import com.lacronicus.outlookclone.util.AnimatorListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DaySelectedListener {

    OutlookApi api;

    OutlookCalendar outlookCalendar;

    CalendarView calendarView;
    AgendaView agendaView;

    MainActivityOnScrollListener scrollListener = new MainActivityOnScrollListener();

    ChronologyContextProvider chronologyContextProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chronologyContextProvider = new ChronologyContextProvider(new Date());//todo inject this object, rather than creating it here. This would allow for inst tests where "today" doesn't vary
        setContentView(R.layout.activity_main);

        Calendar beginningOfYear = Calendar.getInstance();
        CalendarUtils.pushToBeginningOfYear(beginningOfYear);


        outlookCalendar = new OutlookCalendar(beginningOfYear);

        Calendar startOfMonth = Calendar.getInstance();
        startOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        outlookCalendar = new OutlookCalendar(beginningOfYear);

        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setContent(chronologyContextProvider, outlookCalendar);

        agendaView = (AgendaView) findViewById(R.id.agenda_view);
        agendaView.setContent(chronologyContextProvider, outlookCalendar);

        agendaView.setOnScrollListener(scrollListener);
        calendarView.setDaySelectedListener(this);
        loadData();
    }

    public class MainActivityOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            OutlookDay topmostDay = agendaView.getDayForTopmostView();
            if(topmostDay != null) {
                calendarView.setSelectedDay(topmostDay);
            }
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
        if (calendarView.getVisibility() != View.VISIBLE) {
            //enter animation
            calendarView.setVisibility(View.VISIBLE);
            calendarView.setTranslationY(-calendarView.getHeight());
            calendarView.setTranslationX(calendarView.getWidth() / 3);
            calendarView.clearAnimation();
            calendarView.animate()
                    .translationY(0)
                    .translationX(0)
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(300)
                    .setListener(null)
                    .start();
            ObjectAnimator.ofInt(agendaView, AgendaView.PROPERTY_LIST_PADDING_TOP, calendarView.getHeight()).setDuration(300).start() ;
        } else {
            //exit animation
            ObjectAnimator.ofInt(agendaView, AgendaView.PROPERTY_LIST_PADDING_TOP, 0).setDuration(300).start();
            calendarView.animate()
                    .translationY(-calendarView.getHeight())
                    .translationX(calendarView.getWidth() / 3)
                    .scaleX(0.2f)
                    .scaleY(0.2f)
                    .setDuration(300)
                    .setListener(new AnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            calendarView.setVisibility(View.GONE);
                        }
                    }).start();
        }

    }

    public void loadData() {
        api = new MockOutlookApi(getResources().getAssets(), new Gson()); //todo: inject with dagger

        try {
            EventList events = api.getEvents(null, null, null, null);
            outlookCalendar.addItemsToCalendar(events.value);
            calendarView.setContent(chronologyContextProvider, outlookCalendar);
            agendaView.setContent(chronologyContextProvider, outlookCalendar);


        } catch (IOException ioException) {
            setErrorState();
        }
    }

    public void setErrorState() {
        //todo add error state
    }
}
