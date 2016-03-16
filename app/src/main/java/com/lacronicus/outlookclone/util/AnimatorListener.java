package com.lacronicus.outlookclone.util;

import android.animation.Animator;
import android.support.v4.animation.AnimatorListenerCompat;

/**
 * cleans up inline animator listeners by allowing user to only implement callbacks they want
 */
public class AnimatorListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
