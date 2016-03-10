package com.lacronicus.outlookclone.calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.lacronicus.outlookclone.R;

/**
 * Created by fdoyle on 3/8/16.
 */
public class DayView extends FrameLayout implements Checkable, CompoundButton.OnCheckedChangeListener {

    CheckBox dayOfMonthText;
    DaySelectedListener listener;
    DayCellData data;

    public DayView(Context context) {
        super(context);
        init();
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.day_cell, this);
        dayOfMonthText = (CheckBox) findViewById(R.id.day_cell_day_of_month);
        dayOfMonthText.setOnCheckedChangeListener(this);
    }

    public void setContent(DayCellData data) {
        this.data = data;
        String newDayOfMonth = String.valueOf(data.dayOfMonth);
        if(!dayOfMonthText.getText().equals(newDayOfMonth)) {
            dayOfMonthText.setText(newDayOfMonth);
        }
        dayOfMonthText.setEnabled(data.isInSelectedMonth);
        if (dayOfMonthText.isChecked() != data.isSelected) {
            dayOfMonthText.setChecked(data.isSelected);
        }
    }

    public void setOnDaySelectedListener(DaySelectedListener listener) {
        this.listener = listener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        dayOfMonthText.setOnCheckedChangeListener(listener);
    }


    @Override
    public void setChecked(boolean checked) {
        dayOfMonthText.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return dayOfMonthText.isChecked();
    }

    @Override
    public void toggle() {
        dayOfMonthText.toggle();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(listener != null && data.calendarDay != null) {
            listener.onDaySelected(data.calendarDay);
        }
    }
}
