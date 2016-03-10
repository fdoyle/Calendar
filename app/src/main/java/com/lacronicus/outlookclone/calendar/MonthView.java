package com.lacronicus.outlookclone.calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.lacronicus.outlookclone.NoScrollGridLayoutManager;
import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookMonth;



/**
 * Created by fdoyle on 3/8/16.
 */
public class MonthView extends LinearLayout implements DaySelectedListener {

    DayGridAdapter adapter;

    DaySelectedListener listener;

    OutlookMonth month;
    public MonthView(Context context) {
        super(context);
        init();
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_month, this, true);
        RecyclerView dayGrid = (RecyclerView) findViewById(R.id.calendar_page_day_grid);
        adapter = new DayGridAdapter();
        GridLayoutManager gridLayoutManager = new NoScrollGridLayoutManager(getContext(), DayGridAdapter.DAYS_PER_WEEK);
        dayGrid.setLayoutManager(gridLayoutManager);
        dayGrid.setAdapter(adapter);
        dayGrid.setHasFixedSize(true);
        adapter.setDaySelectedListener(this);
    }

    public void setSelectedDay(OutlookDay day) {
        adapter.setDayChecked(day.getDayOfMonth());
    }

    public void setDaySelectedListener(DaySelectedListener listener) {
        this.listener = listener;
    }

    public void setContent(OutlookMonth month) {
        if(!month.equals(this.month)) {
            adapter.setContent(month);
        }
        this.month = month;
    }

    @Override
    public void onDaySelected(OutlookDay day) {
        if(listener != null){
            listener.onDaySelected(day);
        }
    }
}
