package com.lacronicus.outlookclone.eventlist.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.eventlist.viewmodel.DayHeaderViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Header for a given days worth of data in the Agenda View
 */
public class DayHeaderView extends LinearLayout {
    private static final int[] STATE_TODAY = {R.attr.state_today};
    SimpleDateFormat format = new SimpleDateFormat("cccc',' LLLL d", Locale.getDefault()); //ideally, this would be static, but according to http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html this dateformat is invalid, and so unit tests won't run


    TextView headerTextView;
    boolean isToday;


    public DayHeaderView(Context context) {
        super(context);
        init();
    }

    public DayHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DayHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_agenda_day_header, this);
        headerTextView = (TextView) findViewById(R.id.list_item_agenda_day_header_text);
    }

    public void setContent(DayHeaderViewModel viewModel) {
        headerTextView.setText(viewModel.getHeaderText(getContext().getResources(), format));
        boolean newIsTodayValue = viewModel.isToday();
        if(isToday != newIsTodayValue) {
            isToday = newIsTodayValue;
            refreshDrawableState();
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
