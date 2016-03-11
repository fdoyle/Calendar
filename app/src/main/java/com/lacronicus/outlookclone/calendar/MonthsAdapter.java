package com.lacronicus.outlookclone.calendar;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.model.OutlookMonth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by fdoyle on 3/9/16.
 */
public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.EventViewHolder> implements DaySelectedListener {
    List<CalendarMonthViewModel> months = new ArrayList<>();
    Map<OutlookMonth, Integer> monthToIndexMap = new HashMap<>();
    OutlookDay currentlySelectedDay;
    DaySelectedListener listener;

    public void setDaySelectedListener(DaySelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_month, parent, false));
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        MonthView2 monthView = (MonthView2) holder.itemView;
        if (monthView.month == null || !monthView.month.equals(months.get(position).outlookMonth)) {
            monthView.setContent(months.get(position).outlookMonth);
        }
        monthView.setDaySelectedListener(this);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position, List<Object> payloads) {
        MonthView2 monthView = (MonthView2) holder.itemView;
        if (payloads.size() == 0 || !(Boolean) payloads.get(0)) {
            if (monthView.month == null || !monthView.month.equals(months.get(position).outlookMonth)) {
                monthView.setDaySelectedListener(null);
                monthView.setContent(months.get(position).outlookMonth);
                monthView.setDaySelectedListener(this);
            }
        }

        if (currentlySelectedDay != null) {
            monthView.setDaySelectedListener(null);
            monthView.setSelectedDay(currentlySelectedDay);
            monthView.setDaySelectedListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public void setContent(OutlookCalendar calendar) {
        boolean hadExistingContent = months.size() > 0;
        months = new MonthsFlattener().flatten(calendar);

        monthToIndexMap.clear();
        for (int i = 0; i != months.size(); i++) {
            monthToIndexMap.put(months.get(i).outlookMonth, i);
        }
        if (!hadExistingContent) {
            notifyItemRangeInserted(0, months.size());
        } else {
            notifyItemRangeChanged(0, months.size(), false);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedDay(OutlookDay day) {
        if (!day.equals(currentlySelectedDay)) {
            if (currentlySelectedDay != null) {
                int indexOfLastMonth = monthToIndexMap.get(currentlySelectedDay.month);
                months.get(indexOfLastMonth).selectedDay = null;
            }
            int indexOfMonth = monthToIndexMap.get(day.month);
            months.get(indexOfMonth).selectedDay = day;
            notifyItemRangeChanged(0, months.size(), true);
            currentlySelectedDay = day;
        }
    }

    public int getIndexOfMonth(OutlookMonth month) {
        return monthToIndexMap.get(month);
    }

    @Override
    public void onDaySelected(OutlookDay day) {
        if (listener != null) {
            listener.onDaySelected(day);
        }
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public EventViewHolder(View itemView) {
            super(itemView);
        }
    }
}
