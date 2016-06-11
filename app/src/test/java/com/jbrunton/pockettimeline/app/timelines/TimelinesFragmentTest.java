package com.jbrunton.pockettimeline.app.timelines;

import android.support.v7.widget.RecyclerView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.entities.models.Timeline;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestProvidersModule;
import com.jbrunton.pockettimeline.fixtures.TestRepositoriesModule;
import com.jbrunton.pockettimeline.fixtures.shadows.ShadowRecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

import static com.jbrunton.pockettimeline.fixtures.ViewFixtures.getText;
import static com.jbrunton.pockettimeline.fixtures.shadows.ShadowRecyclerView.shadowOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Config(shadows={ShadowRecyclerView.class})
@RunWith(RobolectricGradleTestRunner.class)
public class TimelinesFragmentTest extends FragmentTestSuite<TimelinesFragment, TimelinesFragmentTest.TestApplicationComponent> {
    @Inject TimelinesPresenter presenter;
    final Timeline TIMELINE_ONE = new Timeline("1", "Timeline One", null);
    final Timeline TIMELINE_TWO = new Timeline("2", "Timeline Two", null);

    @Before public void setUp() {
        configureTestSuite(new TimelinesFragment());
        component().inject(this);
        controller().start().resume();
    }

    @Test public void shouldBindPresenter() {
        verify(presenter).bind(fragment());
    }

    @Test public void shouldDisplayTimelines() {
        fragment().showTimelines(asList(TIMELINE_ONE, TIMELINE_TWO));
        RecyclerView timelines = (RecyclerView) fragment().getView().findViewById(R.id.recycler_view);
        shadowOf(timelines).populateItems();

        assertThat(getText(timelines.getChildAt(0), R.id.timeline_title)).isEqualTo(TIMELINE_ONE.getTitle());
        assertThat(getText(timelines.getChildAt(1), R.id.timeline_title)).isEqualTo(TIMELINE_TWO.getTitle());
    }

    @Singleton @Component(modules = {TestProvidersModule.class, TestPresentersModule.class, TestRepositoriesModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent {
        void inject(TimelinesFragmentTest test);
    }

    @Override protected TestApplicationComponent createComponent() {
        return DaggerTimelinesFragmentTest_TestApplicationComponent.create();
    }

    @Module
    public static class TestPresentersModule {
        @Provides @Singleton public TimelinesPresenter provideTimelinesPresenter() {
            return mock(TimelinesPresenter.class);
        }
    }
}
