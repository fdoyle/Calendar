package com.lacronicus.outlookclone.eventlist;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.eventlist.viewmodel.AgendaViewModel;
import com.lacronicus.outlookclone.model.OutlookCalendar;
import com.lacronicus.outlookclone.model.OutlookDay;
import com.lacronicus.outlookclone.util.ChronologyContextProvider;

import java.util.List;
import java.util.Map;

/**
 * Container for the logic associated with the Calendar View
 */
public class AgendaView extends FrameLayout {
    public static final String PROPERTY_LIST_PADDING_TOP = "listPaddingTop";

    Map<OutlookDay, Integer> outlookDayIndexMap;

    RecyclerView agendaList;
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
        agendaList = (RecyclerView) findViewById(R.id.agenda_list);
        final LinearLayoutManager agendaManager = new LinearLayoutManager(getContext());
        agendaList.setLayoutManager(agendaManager);
        agendaAdapter = new AgendaAdapter();
        agendaList.setAdapter(agendaAdapter);
    }

    public void setContent(ChronologyContextProvider chronologyContextProvider, OutlookCalendar outlookCalendar) {
        Pair<List<AgendaViewModel>, Map<OutlookDay, Integer>> flattenedData = new AgendaFlattener(chronologyContextProvider).flatten(outlookCalendar);
        agendaAdapter.setContent(flattenedData.first);
        outlookDayIndexMap = flattenedData.second;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        agendaList.clearOnScrollListeners();
        agendaList.addOnScrollListener(listener);
    }

    public void setSelectedDay(OutlookDay outlookDay) {
        int indexOfDay = outlookDayIndexMap.get(outlookDay);
        ((LinearLayoutManager) agendaList.getLayoutManager()).scrollToPositionWithOffset(indexOfDay, 0);
    }

    public OutlookDay getDayForTopmostView() {
        View firstViewWithinPadding = agendaList.findChildViewUnder(1, agendaList.getPaddingTop() + 1); //get's the first item within the list's padding bounds //todo optimize. this call goes bottom to top, should ideally go top to bottom
        if(firstViewWithinPadding != null) {
            return agendaAdapter.getContent().get(agendaList.getLayoutManager().getPosition(firstViewWithinPadding)).getAssociatedDay();
        } else {
            return null; //if this view is entirely pushed off the screen (say by a calendar or some other component) there may not be a view under that position.
        }
    }


    public void setListPaddingTop(int paddingTop){
        int delta = paddingTop - agendaList.getPaddingTop();
        agendaList.setPadding(agendaList.getPaddingLeft(), paddingTop, agendaList.getPaddingRight(), agendaList.getPaddingBottom());
        agendaList.scrollBy(0,-delta);
    }

    public int getListPaddingTop() {
        return agendaList.getPaddingTop();
    }

}
