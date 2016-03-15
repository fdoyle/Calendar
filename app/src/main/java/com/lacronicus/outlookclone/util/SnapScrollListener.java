package com.lacronicus.outlookclone.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by fdoyle on 3/8/16.
 */
public class SnapScrollListener extends RecyclerView.OnScrollListener {
    RecyclerView listView;

    OnFinishedSnappingListener listener;
    Interpolator interpolator;

    public SnapScrollListener(RecyclerView listView) {
        this.listView = listView;
        interpolator = new DecelerateInterpolator();
    }

    public SnapScrollListener(RecyclerView listView, OnFinishedSnappingListener listener) {
        this.listView = listView;
        this.listener = listener;
        interpolator = new DecelerateInterpolator();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (recyclerView.getChildCount() < 2) { //don't snap with only one item.
            return;
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            InterpolatorSmoothScroller scroller = new InterpolatorSmoothScroller(interpolator, listView); //TODO see if this can be created just once
            if (listView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) listView.getLayoutManager();
                if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                    boolean pastHalfWayVertical = Math.abs(listView.getChildAt(0).getY()) < listView.getChildAt(0).getHeight() / 2;
                    scroller.setTargetPosition(listView.getLayoutManager().getPosition(listView.getChildAt(pastHalfWayVertical ? 0 : 1)));
                } else {
                    boolean pastHalfWayHorizontal = Math.abs(listView.getChildAt(0).getX()) < listView.getChildAt(0).getWidth() / 2;
                    scroller.setTargetPosition(listView.getLayoutManager().getPosition(listView.getChildAt(pastHalfWayHorizontal ? 0 : 1)));
                }
                if (scroller.wouldMoveSignificantly()) { // don't animate if you're already on your target
                    layoutManager.startSmoothScroll(scroller);
                } else {
                    if(listener != null) {
                        listener.onSettled();
                    }
                }
            } else {
                throw new IllegalStateException("This only works with LinearLayoutManagers");
            }
        }
    }

    public interface OnFinishedSnappingListener {
        void onSettled();
    }

}
