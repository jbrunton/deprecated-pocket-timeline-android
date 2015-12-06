package com.jbrunton.pockettimeline.app.timelines;

import android.support.v7.widget.RecyclerView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestProvidersModule;
import com.jbrunton.pockettimeline.fixtures.shadows.ShadowRecyclerView;
import com.jbrunton.pockettimeline.models.Timeline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import rx.Observable;

import static com.jbrunton.pockettimeline.fixtures.ViewFixtures.getText;
import static com.jbrunton.pockettimeline.fixtures.matchers.LoadingIndicatorMatcher.isDisplayingLoadingIndicator;
import static com.jbrunton.pockettimeline.fixtures.matchers.LoadingIndicatorMatcher.isNotDisplayingLoadingIndicator;
import static com.jbrunton.pockettimeline.fixtures.shadows.ShadowRecyclerView.shadowOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@Config(shadows={ShadowRecyclerView.class})
@RunWith(RobolectricGradleTestRunner.class)
public class TimelinesFragmentTest extends FragmentTestSuite<TimelinesFragment, TimelinesFragmentTest.TestApplicationComponent> {
    @Inject TimelinesProvider timelinesProvider;
    final Timeline TIMELINE_ONE = new Timeline("1", "Timeline One", null);
    final Timeline TIMELINE_TWO = new Timeline("2", "Timeline Two", null);

    @Before public void setUp() {
        configureTestSuite(new TimelinesFragment());

        component().inject(this);
        stubProviderToReturn(TIMELINE_ONE, TIMELINE_TWO);

        controller().start();
    }

    @Test public void shouldShowLoadingIndicatorAtFirst() {
        when(timelinesProvider.getTimelines()).thenReturn(Observable.from(Collections.emptyList()));

        controller().resume();

        assertThat(fragment(), isDisplayingLoadingIndicator());
    }

    @Test public void shouldHideLoadingIndicatorWhenResultsAvailable() {
        controller().resume();
        assertThat(fragment(), isNotDisplayingLoadingIndicator());
    }

    @Test public void shouldDisplayTimelines() {
        controller().resume();
        RecyclerView timelines = (RecyclerView) fragment().getView().findViewById(R.id.recycler_view);
        shadowOf(timelines).populateItems();

        assertThat(getText(timelines.getChildAt(0), R.id.timeline_title)).isEqualTo(TIMELINE_ONE.getTitle());
        assertThat(getText(timelines.getChildAt(1), R.id.timeline_title)).isEqualTo(TIMELINE_TWO.getTitle());
    }

    private void stubProviderToReturn(Timeline... timelines) {
        when(timelinesProvider.getTimelines()).thenReturn(Observable.just(Arrays.asList(timelines)));
    }

    @Singleton @Component(modules = {RestServiceModule.class, TestProvidersModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent {
        void inject(TimelinesFragmentTest test);
    }

    @Override protected TestApplicationComponent createComponent() {
        return DaggerTimelinesFragmentTest_TestApplicationComponent.create();
    }
}
