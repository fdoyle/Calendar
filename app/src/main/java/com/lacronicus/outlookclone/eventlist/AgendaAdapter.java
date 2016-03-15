package com.lacronicus.outlookclone.eventlist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.eventlist.view.DayHeaderView;
import com.lacronicus.outlookclone.eventlist.view.EmptyDayView;
import com.lacronicus.outlookclone.eventlist.view.EventView;
import com.lacronicus.outlookclone.eventlist.viewmodel.AgendaViewModel;
import com.lacronicus.outlookclone.eventlist.viewmodel.DayHeaderViewModel;
import com.lacronicus.outlookclone.eventlist.viewmodel.EmptyDayViewModel;
import com.lacronicus.outlookclone.eventlist.viewmodel.EventViewModel;
import com.lacronicus.outlookclone.model.OutlookCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the list of items in the Agenda View
 */
public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.EventListItemViewHolder>{

    public static final int TYPE_DAY_HEADER = 0;
    public static final int TYPE_EVENT = 1;
    public static final int TYPE_EMPTY_DAY = 2;

    List<AgendaViewModel> content;

    public AgendaAdapter() {
        this.content = new ArrayList<>();
    }

    @Override
    public EventListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case TYPE_DAY_HEADER:
                return new EventListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_day_header, parent, false));
            case TYPE_EVENT:
                return new EventListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false));
            case TYPE_EMPTY_DAY:
            default:
                return new EventListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_agenda_empty_day, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(EventListItemViewHolder holder, int position) {
        switch(getItemViewType(position)) {
            case TYPE_DAY_HEADER:
                DayHeaderView dayHeaderView = (DayHeaderView) holder.itemView;
                dayHeaderView.setContent((DayHeaderViewModel) content.get(position));
                break;
            case TYPE_EVENT:
                EventView eventView = (EventView) holder.itemView;
                eventView.setContent((EventViewModel) content.get(position));
                break;
            case TYPE_EMPTY_DAY:
                EmptyDayView emptyDayView = (EmptyDayView) holder.itemView;
                emptyDayView.setContent((EmptyDayViewModel) content.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        AgendaViewModel model = content.get(position);
        if(model instanceof DayHeaderViewModel)
            return TYPE_DAY_HEADER;
        if(model instanceof EventViewModel)
            return TYPE_EVENT;
        if(model instanceof EmptyDayView)
            return TYPE_EMPTY_DAY;
        return -1;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }


    public void setContent(List<AgendaViewModel> calendar) {
        this.content = calendar;
        notifyDataSetChanged(); //todo a proper diffing implementation between new list and old
    }

    public List<AgendaViewModel> getContent() {
        return content;
    }

    public static class EventListItemViewHolder extends RecyclerView.ViewHolder{
        public EventListItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
