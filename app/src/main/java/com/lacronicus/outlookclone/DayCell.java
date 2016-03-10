package com.lacronicus.outlookclone;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by fdoyle on 3/8/16.
 */
public class DayCell extends FrameLayout{

    public DayCell(Context context) {
        super(context);
        init();
    }

    public DayCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DayCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.day_cell, this);

    }

}
