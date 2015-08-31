package com.jbrunton.pockettimeline.app.quiz;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jbrunton.pockettimeline.BuildConfig;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.models.Event;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
public class QuizFragmentTest extends FragmentTestSuite<QuizFragment> {
    @Inject EventsProvider eventsProvider;

    @Before public void setUp() {
        TestApplicationComponent component = DaggerQuizFragmentTest_TestApplicationComponent.create();
        ((PocketTimelineApplication) RuntimeEnvironment.application).setComponent(component);

        component.inject(this);

        configure(new QuizFragment());
    }

    @Test public void shouldDisplayEventDetailsOnResume() {
        Event event = new Event("1", new LocalDate(2001, 1, 1), "Some Event", "Some description");
        when(eventsProvider.getEvents()).thenReturn(Observable.just(event).toList());

        controller().start().resume();

        assertThat(((TextView) fragment().getView().findViewById(R.id.event_title)).getText()).isEqualTo(event.getTitle());
    }

    @Singleton @Module
    static class TestEventsProviderModel {
        @Singleton @Provides EventsProvider provide() {
            return mock(EventsProvider.class);
        }
    }

    @Singleton @Component(modules = {TestEventsProviderModel.class, RestServiceModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent {
        void inject(QuizFragmentTest test);
    }

}
