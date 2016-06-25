package com.jbrunton.pockettimeline;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jbrunton.pockettimeline.app.HomeActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void shouldDisplayTimelines() {
        Spoon.screenshot(activityRule.getActivity(), "displayed");
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("World War II"))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("20th Century Iran"))));
    }
}