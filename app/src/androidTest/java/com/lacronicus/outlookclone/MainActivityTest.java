package com.lacronicus.outlookclone;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by fdoyle on 3/21/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test_that_calendar_starts_invisible() {
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void test_that_calendar_becomes_visible_on_calendar_toggle_button_clicked() {
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        Espresso.onView(ViewMatchers.withId(R.id.action_toggle_calendar)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}