package com.lacronicus.outlookclone.calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookMonth;
import com.lacronicus.outlookclone.util.ChronologyContextProvider;

import java.util.Calendar;
import java.util.Locale;

/**
 * View displaying a particular month in a Calendar
 */
public class MonthView extends FrameLayout implements DaySelectedListener {
    public static final int DAYS_PER_WEEK = 7;
    public static final int WEEKS_DISPLAYED = 6;
    public static final int CELL_COUNT = DAYS_PER_WEEK * WEEKS_DISPLAYED;

    DaySelectedListener daySelectedListener;

    TextView monthTitle;


    int indexOfFirstDayInMonth;

    int currentlySelectedDay = -1; //starts at 1
    int indexOfCurrentlySelectedDay = -1; // starts at 0
    OutlookMonth month;

    DayView[] dayViews = new DayView[CELL_COUNT];

    int maxDaysInMonth;


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

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_month_grid, this);
        GridLayout grid = (GridLayout) findViewById(R.id.view_month_grid);
        monthTitle = (TextView) findViewById(R.id.month_grid_title);
        for (int i = 0; i != CELL_COUNT; i++) {
            View cellWrapper = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_day_cell, grid, false); //the cellWrapper FrameLayout exists to expand, allowing the cell itself to remain a perfect circle
            DayView cell = (DayView) cellWrapper.findViewById(R.id.day_cell_day_of_month);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1.0f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1/0f);
            cellWrapper.setLayoutParams(params);
            grid.addView(cellWrapper);
            dayViews[i] = cell;
        }
    }

    public void setContent(ChronologyContextProvider chronologyContextProvider, OutlookMonth monthToDisplay) {
        currentlySelectedDay = -1;
        indexOfCurrentlySelectedDay = -1;
        indexOfFirstDayInMonth = -1;
        maxDaysInMonth = monthToDisplay.getStartOfMonth().getActualMaximum(Calendar.DAY_OF_MONTH);

        String title = monthToDisplay.getStartOfMonth().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        if (!chronologyContextProvider.isDateWithinSameYear(monthToDisplay.getStartOfMonth().getTime())) {
            title = title + " " + monthToDisplay.getStartOfMonth().get(Calendar.YEAR);
        }
        monthTitle.setText(title);
        this.month = monthToDisplay;
        Calendar firstDayOfMonth = monthToDisplay.getStartOfMonth();
        firstDayOfMonth.setFirstDayOfWeek(Calendar.SUNDAY);//if you wanted to change the first day of the week, this is where you'd do it. Change this, everything "should" just fall into place


        Calendar firstDayInGrid = (Calendar) firstDayOfMonth.clone();
        firstDayInGrid.set(Calendar.DAY_OF_WEEK, firstDayInGrid.getFirstDayOfWeek()); //may already be first day of week, that's fine.

        Calendar calendarForDayAtIndex = (Calendar) firstDayInGrid.clone();
        for (int i = 0; i != CELL_COUNT; i++) {
            long startOfDayAtIndex = calendarForDayAtIndex.getTimeInMillis();
            boolean dayHasEvents;
            boolean isInSelectedMonth;
            OutlookDay calendarDayAtIndex = null;
            if (calendarForDayAtIndex.get(Calendar.MONTH) == firstDayOfMonth.get(Calendar.MONTH)) {
                isInSelectedMonth = true;
                calendarDayAtIndex = monthToDisplay.days.get(calendarForDayAtIndex.get(Calendar.DAY_OF_MONTH) - 1);

                if (calendarForDayAtIndex.get(Calendar.DAY_OF_MONTH) == 1) {
                    indexOfFirstDayInMonth = i;
                }
                dayHasEvents = calendarDayAtIndex.hasAnyEvents(); //day 1 of month is at index 0
            } else {
                isInSelectedMonth = false;
                dayHasEvents = false; //not in this month, don't display anything
            }
            int dayOfMonth = calendarForDayAtIndex.get(Calendar.DAY_OF_MONTH);
            boolean isInToday = chronologyContextProvider.isDateWithinToday(calendarForDayAtIndex.getTime());
            calendarForDayAtIndex.add(Calendar.DAY_OF_YEAR, 1); //increment to end of day/beginning of next day

            //don't include beginning of next day within today
            calendarForDayAtIndex.add(Calendar.MILLISECOND, -1);
            long endOfDayAtIndex = calendarForDayAtIndex.getTimeInMillis();
            calendarForDayAtIndex.add(Calendar.MILLISECOND, 1);

            DayCellViewModel dataForDay = new DayCellViewModel(calendarDayAtIndex,
                    dayOfMonth,
                    startOfDayAtIndex,
                    endOfDayAtIndex,
                    dayHasEvents,
                    isInSelectedMonth,
                    false,
                    isInToday);

            dayViews[i].setContent(dataForDay);
            dayViews[i].setOnDaySelectedListener(this);
        }
    }

    public void setSelectedDay(OutlookDay day) {
        if (day != null && day.month != null && day.month.equals(month)) {
            if (indexOfCurrentlySelectedDay != -1 && indexOfCurrentlySelectedDay < dayViews.length) {
                setChecked(dayViews[indexOfCurrentlySelectedDay], false);
            }
            indexOfCurrentlySelectedDay = day.getDayOfMonth() + indexOfFirstDayInMonth - 1;
            currentlySelectedDay = day.getDayOfMonth();
            setChecked(dayViews[indexOfCurrentlySelectedDay], true);
        } else {
            if (indexOfCurrentlySelectedDay != -1 && indexOfCurrentlySelectedDay < dayViews.length) {
                setChecked(dayViews[indexOfCurrentlySelectedDay], false);
            }
        }
    }

    public void setDaySelectedListener(DaySelectedListener listener) {
        this.daySelectedListener = listener;
    }

    private void setChecked(DayView view, boolean checked) {
        view.setOnDaySelectedListener(null);
        view.setChecked(checked);
        view.setOnDaySelectedListener(this);

    }

    @Override
    public void onDaySelected(OutlookDay day) {
        setSelectedDay(day);
        if (daySelectedListener != null) {
            daySelectedListener.onDaySelected(day);
        }
    }
}
