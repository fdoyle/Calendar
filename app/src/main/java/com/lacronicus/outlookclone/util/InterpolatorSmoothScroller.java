package com.lacronicus.outlookclone.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by fdoyle on 3/8/16.
 */
public class InterpolatorSmoothScroller extends RecyclerView.SmoothScroller {
    Interpolator interpolator;
    boolean shouldStart = true;
    RecyclerView recyclerView;


    public InterpolatorSmoothScroller(Interpolator interpolator, RecyclerView recyclerView) {
        this.interpolator = interpolator;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onStart() {
        shouldStart = true;
    }

    @Override
    protected void onStop() {
    }

    @Override
    protected void onSeekTargetStep(int dx, int dy, RecyclerView.State state, Action action) {
        scroll(state, action, 3000);
    }

    @Override
    protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
        scroll(state, action, 1000);
    }

    private void scroll(RecyclerView.State state, Action action, int duration) {
        if(state.didStructureChange() || shouldStart) {
            int targetIndex = state.getTargetScrollPosition();
            View currentView = recyclerView.getLayoutManager().getChildAt(0);
            int currentIndex = recyclerView.getLayoutManager().getPosition(recyclerView.getChildAt(0));
            int yDelta = currentView.getHeight() * (targetIndex - currentIndex) + (int) currentView.getY();
            int xDelta = currentView.getWidth() * (targetIndex - currentIndex) + (int) currentView.getX();

            action.setDy(yDelta);
            action.setDx(xDelta);
            if(Math.abs(xDelta) < 10 && Math.abs(yDelta) < 10) {
                action.setDuration(1);
            } else {
                action.setDuration(duration);
                action.setInterpolator(interpolator);
            }
            shouldStart = false;
            stop();
        }
    }

    public int getAbsYDelta(int target) {
        View currentView = recyclerView.getLayoutManager().getChildAt(0);
        int currentIndex = recyclerView.getLayoutManager().getPosition(recyclerView.getChildAt(0));
        return Math.abs(currentView.getHeight() * (target - currentIndex) + (int) currentView.getY());
    }

    public int getAbsXDelta(int target) {
        View currentView = recyclerView.getLayoutManager().getChildAt(0);
        int currentIndex = recyclerView.getLayoutManager().getPosition(recyclerView.getChildAt(0));
        return Math.abs(currentView.getWidth() * (target - currentIndex) + (int) currentView.getX());

    }

    public boolean wouldMoveSignificantly() {
        return getAbsXDelta(getTargetPosition()) > 3 || getAbsYDelta(getTargetPosition()) > 3;
    }


}