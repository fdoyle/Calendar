package com.lacronicus.outlookclone.calendar;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookMonth;

import java.util.Calendar;

public class DayGridAdapter extends RecyclerView.Adapter<DayGridAdapter.DayCellViewHolder> {

    DaySelectedListener daySelectedListener;

    public static final int DAYS_PER_WEEK = 7;
    public static final int WEEKS_DISPLAYED = 6;
    public static final int CELL_COUNT = DAYS_PER_WEEK * WEEKS_DISPLAYED;

    DayCellData[] dayCellData = new DayCellData[CELL_COUNT];

    int indexOfFirstDayInMonth;

    int currentlySelectedDay = -1; //starts at 1
    int indexOfCurrentlySelectedDay = -1; // starts at 0
    OutlookMonth month;

    int maxDaysInMonth;

    public void setDaySelectedListener(DaySelectedListener listener) {
        this.daySelectedListener = listener;
    }

    public void setContent(OutlookMonth monthToDisplay) {
        currentlySelectedDay = -1;
        indexOfCurrentlySelectedDay = -1;
        indexOfFirstDayInMonth = -1;
        maxDaysInMonth = monthToDisplay.getStartOfMonth().getActualMaximum(Calendar.DAY_OF_MONTH);
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

                if(calendarForDayAtIndex.get(Calendar.DAY_OF_MONTH) == 1) {
                    indexOfFirstDayInMonth = i;
                }
                dayHasEvents = calendarDayAtIndex.hasAnyEvents(); //day 1 of month is at index 0
            } else {
                isInSelectedMonth = false;
                dayHasEvents = false; //not in this month, don't display anything
            }
            int dayOfMonth = calendarForDayAtIndex.get(Calendar.DAY_OF_MONTH);
            calendarForDayAtIndex.add(Calendar.DAY_OF_YEAR, 1); //increment to end of day/beginning of next day
            long endOfDayAtIndex = calendarForDayAtIndex.getTimeInMillis();
            dayCellData[i] = new DayCellData(calendarDayAtIndex,dayOfMonth, startOfDayAtIndex, endOfDayAtIndex, dayHasEvents, isInSelectedMonth, false);
        }

        Log.d("TAG", "daygrid setcontent notify");
        notifyDataSetChanged();
    }

    @Override
    public DayCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayCellViewHolder(new DayView(parent.getContext()));
    }

    @Override
    public int getItemViewType(int position) {
        return dayCellData[position].dayOfMonth;
    }

    @Override
    public void onBindViewHolder(final DayCellViewHolder holder, final int position) {
        holder.dayView.setOnCheckedChangeListener(null);// don't tell items about their own checked changes
        holder.setContent(dayCellData[position]);
        holder.dayView.setOnCheckedChangeListener(holder);
    }

    public DayCellData getItemAtIndex(int index) {
        return dayCellData[index];
    }

    @Override
    public int getItemCount() {
        return CELL_COUNT;
    }


    /**
     * set day of month checked, starting at 1
    * */
    public void setDayChecked(int dayOfMonthToCheck) {
        if(dayOfMonthToCheck > maxDaysInMonth) {
            dayOfMonthToCheck = maxDaysInMonth;
        }
        if(dayOfMonthToCheck != currentlySelectedDay) { // only change if you need to.
            if (indexOfCurrentlySelectedDay != -1) {
                dayCellData[indexOfCurrentlySelectedDay].isSelected = false;
                notifyItemChanged(indexOfCurrentlySelectedDay);
            }
            int indexOfDayOfMonthToCheck = dayOfMonthToCheck + indexOfFirstDayInMonth - 1;
            dayCellData[indexOfDayOfMonthToCheck].isSelected = true;
            notifyItemChanged(indexOfDayOfMonthToCheck);
            currentlySelectedDay = dayOfMonthToCheck;
            indexOfCurrentlySelectedDay = indexOfDayOfMonthToCheck;
        }
        if(daySelectedListener != null) {
            if(dayOfMonthToCheck < month.days.size() - 1)
            daySelectedListener.onDaySelected(month.days.get(dayOfMonthToCheck));
        }
    }

    public class DayCellViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        DayView dayView;
        DayCellData data;

        public DayCellViewHolder(View itemView) {
            super(itemView);
            dayView = (DayView) itemView;
        }

        public void setContent(DayCellData data) {
            this.data = data;
            dayView.setContent(data);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            boolean isDayInMonth = data.calendarDay != null;
            if (currentlySelectedDay != -1 && isDayInMonth && currentlySelectedDay == data.calendarDay.getDayOfMonth() - 1 && isChecked == false) {
                buttonView.setChecked(true); // don't uncheck a day that's still supposed to be checked.
            }
            if (isChecked) {
                if (data.calendarDay != null) {
                    setDayChecked(data.calendarDay.getDayOfMonth());
                }
            }
        }
    }

}
