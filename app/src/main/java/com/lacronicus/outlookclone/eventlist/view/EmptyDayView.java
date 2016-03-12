package com.lacronicus.outlookclone.eventlist.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.lacronicus.outlookclone.R;
import com.lacronicus.outlookclone.eventlist.viewmodel.EmptyDayViewModel;

/**
 * Created by fdoyle on 3/9/16.
 */
public class EmptyDayView extends FrameLayout {
    public EmptyDayView(Context context) {
        super(context);
        init();
    }

    public EmptyDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyDayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_empty_day, this);
    }

    public void setContent(EmptyDayViewModel viewModel) {

    }

}
