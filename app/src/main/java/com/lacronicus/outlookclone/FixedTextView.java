package com.lacronicus.outlookclone;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by fdoyle on 3/11/16.
 */
public class FixedTextView extends TextView {

    public FixedTextView(Context context) {
        super(context);
    }

    public FixedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FixedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }
}
