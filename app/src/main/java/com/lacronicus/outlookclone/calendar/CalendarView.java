package com.lacronicus.outlookclone.calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.SnapScrollListener;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.model.OutlookDay;

/**
 * Created by fdoyle on 3/9/16.
 */
public class CalendarView extends FrameLayout implements SnapScrollListener.OnFinishedSnappingListener, DaySelectedListener {

    OutlookDay selectedDay;

    RecyclerView calendarPager;
    MonthsAdapter monthsAdapter;
    DaySelectedListener listener;

    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setContent(OutlookCalendar calendar) {
        monthsAdapter.setContent(calendar);
    }

    public void setDaySelectedListener(DaySelectedListener listener) {
        this.listener = listener;
    }

    public void setSelectedDay(OutlookDay newSelectedDay) {
        if (!newSelectedDay.equals(selectedDay)) {
            this.selectedDay = newSelectedDay;
            int indexOfMonth = monthsAdapter.getIndexOfMonth(newSelectedDay.month);
            calendarPager.smoothScrollToPosition(indexOfMonth);
            monthsAdapter.setSelectedDay(selectedDay);
        }
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_calendar, this);
        calendarPager = (RecyclerView) findViewById(R.id.calendar_list);
        final LinearLayoutManager calendarManager = new LinearLayoutManager(getContext());
        calendarManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        calendarPager.setLayoutManager(calendarManager);
        calendarPager.setHasFixedSize(true);
        monthsAdapter = new MonthsAdapter();
        calendarPager.getItemAnimator().setChangeDuration(0);
        calendarPager.getItemAnimator().setMoveDuration(0);
        calendarPager.setAdapter(monthsAdapter);
        calendarPager.addOnScrollListener(new SnapScrollListener(calendarPager, this));
        calendarPager.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        calendarPager.requestDisallowInterceptTouchEvent(true);
        calendarPager.setHasFixedSize(true);
        monthsAdapter.setDaySelectedListener(this);
    }

    @Override
    public void onSettled() {
        //may or may not need this
    }

    @Override
    public void onDaySelected(OutlookDay day) {
        if(listener != null) {
            listener.onDaySelected(day);
        }
    }
}
