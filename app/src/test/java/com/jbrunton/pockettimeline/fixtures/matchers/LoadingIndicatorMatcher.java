package com.jbrunton.pockettimeline.fixtures.matchers;

import android.view.View;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorFragment;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class LoadingIndicatorMatcher extends TypeSafeMatcher<LoadingIndicatorFragment> {
    private final boolean loadingIndicatorShouldBeVisible;

    private LoadingIndicatorMatcher(boolean loadingIndicatorShouldBeVisible) {
        this.loadingIndicatorShouldBeVisible = loadingIndicatorShouldBeVisible;
    }

    public static LoadingIndicatorMatcher isDisplayingLoadingIndicator() {
        return new LoadingIndicatorMatcher(true);
    }

    public static LoadingIndicatorMatcher isNotDisplayingLoadingIndicator() {
        return new LoadingIndicatorMatcher(false);
    }

    public static LoadingIndicatorMatcher isDisplayingContent() {
        return isNotDisplayingLoadingIndicator();
    }

    @Override
    protected boolean matchesSafely(LoadingIndicatorFragment fragment) {
        boolean loadingIndicatorIsVisible = loadingIndicatorVisible(fragment);
        boolean contentIsVisible = contentVisible(fragment);

        if (loadingIndicatorShouldBeVisible) {
            return loadingIndicatorIsVisible && !contentIsVisible;
        }
        else {
            return !loadingIndicatorIsVisible && contentIsVisible;
        }
    }

    @Override
    public void describeTo(Description description) {
        if (loadingIndicatorShouldBeVisible) {
            description.appendText("indicator is Visible and content is not Visible");
        }
        else {
            description.appendText("indicator is not Visible and content is Visible");
        }
    }

    @Override
    protected void describeMismatchSafely(LoadingIndicatorFragment fragment, Description description) {
        boolean loadingIndicatorIsVisible = loadingIndicatorVisible(fragment);
        boolean contentIsVisible = contentVisible(fragment);

        description.appendText(loadingIndicatorIsVisible ? "indicator is Visible" : "indicator is not Visible");
        description.appendText(" and ");
        description.appendText(contentIsVisible ? "content is Visible" : "content is not Visible");
    }

    private boolean contentVisible(LoadingIndicatorFragment fragment) {
        return fragment.getView().findViewById(R.id.content_holder).getVisibility() == View.VISIBLE;
    }

    private boolean loadingIndicatorVisible(LoadingIndicatorFragment fragment) {
        return fragment.getView().findViewById(R.id.progress_bar).getVisibility() == View.VISIBLE;
    }
}