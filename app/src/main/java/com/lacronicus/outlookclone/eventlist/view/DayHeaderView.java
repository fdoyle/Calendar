package com.lacronicus.outlookclone.eventlist.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.eventlist.viewmodel.DayHeaderViewModel;

/**
 * Created by fdoyle on 3/9/16.
 */
public class DayHeaderView extends FrameLayout {
    TextView headerTextView;

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
        LayoutInflater.from(getContext()).inflate(R.layout.list_item_agenda_day_header, this);
        headerTextView = (TextView) findViewById(R.id.list_item_agenda_day_header_text);
    }

    public void setContent(DayHeaderViewModel viewModel) {
        headerTextView.setText(viewModel.getHeaderText());
    }
}
