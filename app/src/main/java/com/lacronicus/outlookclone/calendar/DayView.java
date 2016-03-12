package com.lacronicus.outlookclone.calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lacronicus.outlookclone.R;

/**
 * Created by fdoyle on 3/8/16.
 */
public class DayView extends CheckBox implements CompoundButton.OnCheckedChangeListener {

    private static final int[] STATE_TODAY = {R.attr.state_today};
    private static final int[] STATE_WITHIN_MONTH = {R.attr.state_within_month};

    DaySelectedListener listener;
    DayCellViewModel data;

    boolean isToday = false;

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
        setOnCheckedChangeListener(this);
    }

    public void setContent(DayCellViewModel data) {
        this.data = data;
        String newDayOfMonth = String.valueOf(data.dayOfMonth);
        if(!getText().equals(newDayOfMonth)) {
            setText(newDayOfMonth);
        }
        setEnabled(data.isInSelectedMonth);
        if (isChecked() != data.isSelected) {
            setChecked(data.isSelected);
        }


        boolean newIsTodayValue = data.isToday;
        if(isToday != newIsTodayValue) {
            isToday = newIsTodayValue;
            refreshDrawableState();
        }
    }

    public void setOnDaySelectedListener(DaySelectedListener listener) {
        this.listener = listener;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(listener != null && data.calendarDay != null) {
            listener.onDaySelected(data.calendarDay);
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if(isToday) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace +1);
            this.mergeDrawableStates(drawableState, STATE_TODAY);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }
}
