package com.jbrunton.pockettimeline.app.quiz;

import android.widget.TextView;

import com.jbrunton.pockettimeline.Injects;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.ProvidersModule;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestProvidersModule;
import com.jbrunton.pockettimeline.models.Event;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
public class QuizFragmentTest extends FragmentTestSuite<QuizFragment> {
    @Inject EventsProvider eventsProvider;

    @Before public void setUp() {
        configureTestSuite()
                .fragment(new QuizFragment())
                .inject();
    }

    @Test public void shouldDisplayEventDetailsOnResume() {
        Event event = createEvent();
        stubProviderToReturn(event);

        controller().start().resume();

        assertThat(((TextView) fragment().getView().findViewById(R.id.event_title)).getText()).isEqualTo(event.getTitle());
    }

    private Event createEvent() {
        return new Event("1", new LocalDate(2001, 1, 1), "Some Event", "Some description");
    }

    private void stubProviderToReturn(Event event) {
        when(eventsProvider.getEvents()).thenReturn(Observable.just(event).toList());
    }

    @Singleton @Component(modules = {RestServiceModule.class, TestProvidersModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent, Injects<QuizFragmentTest> {
        void inject(QuizFragmentTest test);
    }

    @Override protected ApplicationComponent createComponent() {
        return DaggerQuizFragmentTest_TestApplicationComponent.create();
    }
}
