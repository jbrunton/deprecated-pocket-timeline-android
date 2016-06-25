package com.jbrunton.pockettimeline;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jbrunton.pockettimeline.app.HomeActivity;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void shouldDisplayTimelines() {
        Spoon.screenshot(activityRule.getActivity(), "timelines");
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("World War II"))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("20th Century Iran"))));
    }

    @Test
    public void shouldExpandDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        Spoon.screenshot(activityRule.getActivity(), "drawer_open");
        onView(withId(R.id.nav_view))
                .check(matches(isCompletelyDisplayed()));
        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText("Timelines")))
                .check(matches(isCompletelyDisplayed()));
        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText("Quiz")))
                .check(matches(isCompletelyDisplayed()));
        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText("Search")))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void shouldShowQuizFragment() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText("Quiz")))
                .perform(click());

        Spoon.screenshot(activityRule.getActivity(), "quiz_fragment");
        onView(allOf(instanceOf(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText("Quiz")));
    }

    @Test
    public void shouldShowSearchFragment() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        onView(allOf(isDescendantOfA(withId(R.id.nav_view)), withText("Search")))
                .perform(click());

        Spoon.screenshot(activityRule.getActivity(), "search_fragment");
        onView(allOf(instanceOf(TextView.class), withParent(isAssignableFrom(Toolbar.class))))
                .check(matches(withText("Search")));
    }
}