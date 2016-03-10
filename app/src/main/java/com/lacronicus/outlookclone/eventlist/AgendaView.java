package com.lacronicus.outlookclone.eventlist;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.eventlist.viewmodel.AgendaViewModel;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.model.OutlookDay;

import java.util.List;
import java.util.Map;

/**
 * Created by fdoyle on 3/10/16.
 */
public class AgendaView extends FrameLayout {

    Map<OutlookDay, Integer> outlookDayIndexMap;

    RecyclerView agendaView;
    AgendaAdapter agendaAdapter;

    public AgendaView(Context context) {
        super(context);
        init();
    }

    public AgendaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AgendaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AgendaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_agenda, this);
        agendaView = (RecyclerView) findViewById(R.id.agenda_list);
        final LinearLayoutManager agendaManager = new LinearLayoutManager(getContext());
        agendaView.setLayoutManager(agendaManager);
        agendaAdapter = new AgendaAdapter();
        agendaView.setAdapter(agendaAdapter);
    }

    public void setContent(OutlookCalendar outlookCalendar) {
        Pair<List<AgendaViewModel>, Map<OutlookDay, Integer>> flattenedData = new AgendaFlattener().flatten(outlookCalendar);
        agendaAdapter.setContent(flattenedData.first);
        outlookDayIndexMap = flattenedData.second;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        agendaView.clearOnScrollListeners();
        agendaView.addOnScrollListener(listener);
    }

    public void setSelectedDay(OutlookDay outlookDay) {
        int indexOfDay = outlookDayIndexMap.get(outlookDay);
        ((LinearLayoutManager) agendaView.getLayoutManager()).scrollToPositionWithOffset(indexOfDay, 0);
    }

    public OutlookDay getDayForTopmostView() {
        return agendaAdapter.getContent().get(agendaView.getLayoutManager().getPosition(agendaView.getChildAt(0))).getAssociatedDay();
    }
}
