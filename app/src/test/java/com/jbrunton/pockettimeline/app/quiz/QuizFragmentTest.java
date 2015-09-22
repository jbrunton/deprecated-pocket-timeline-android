package com.jbrunton.pockettimeline.app.quiz;

import android.app.Dialog;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.fixtures.DeterministicRandomHelperModule;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestProvidersModule;
import com.jbrunton.pockettimeline.models.Event;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import rx.Observable;

import static com.jbrunton.pockettimeline.fixtures.AlertDialogFixtures.alertDialogButton;
import static com.jbrunton.pockettimeline.fixtures.AlertDialogFixtures.alertDialogMessage;
import static com.jbrunton.pockettimeline.fixtures.AlertDialogFixtures.alertDialogShowing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
public class QuizFragmentTest extends FragmentTestSuite<QuizFragment, QuizFragmentTest.TestApplicationComponent> {
    @Inject EventsProvider eventsProvider;
    final Event EVENT_ONE = new Event("1", new LocalDate(2014, DateTimeConstants.JUNE, 3), "Event One", null);
    final Event EVENT_TWO = new Event("2", new LocalDate(2015, DateTimeConstants.JUNE, 4), "Event Two", null);

    @Before public void setUp() {
        configureTestSuite(new QuizFragment());

        component().inject(this);
        stubProviderToReturn(EVENT_ONE, EVENT_TWO);

        controller().start().resume();
    }

    @Test public void shouldDisplayEventDetails() {
        assertThat(eventTitle()).isEqualTo(EVENT_TWO.getTitle());
    }

    @Test public void shouldRespondToCorrectAnswer() {
        submitAnswer(EVENT_TWO.getDate());
        assertThat(alertDialogMessage()).isEqualTo(getString(R.string.correct_answer));
    }

    @Test public void shouldRespondToIncorrectAnswer() {
        submitAnswer(EVENT_ONE.getDate());
        assertThat(alertDialogMessage()).isEqualTo(getString(R.string.incorrect_answer, "2015"));
    }

    @Test public void shouldAllowResponseDialogToBeDismissed() {
        submitAnswer(EVENT_TWO.getDate());
        assertThat(alertDialogShowing()).isTrue();

        alertDialogButton(Dialog.BUTTON_POSITIVE).performClick();

        assertThat(alertDialogShowing()).isFalse();
    }

    @Test public void shouldAskTheNextQuestionAfterAnswer() {
        submitAnswer(EVENT_TWO.getDate());
        alertDialogButton(Dialog.BUTTON_POSITIVE).performClick();
        assertThat(eventTitle()).isEqualTo(EVENT_ONE.getTitle());
    }

    @Test public void shouldClearAnswerFieldAfterDialogDismissed() {
        submitAnswer("some answer");
        assertThat(answer()).isNotEmpty();

        alertDialogButton(Dialog.BUTTON_POSITIVE).performClick();

        assertThat(answer()).isEmpty();
    }

    private void stubProviderToReturn(Event... events) {
        when(eventsProvider.getEvents()).thenReturn(Observable.just(Arrays.asList(events)));
    }

    private void submitAnswer(LocalDate date) {
        submitAnswer(Integer.toString(date.getYear()));
    }

    private void submitAnswer(String answer) {
        textView(R.id.answer).setText(answer);
        button(R.id.submit).performClick();
    }

    private String eventTitle() {
        return textView(R.id.event_title).getText().toString();
    }

    private String answer() {
        return textView(R.id.answer).getText().toString();
    }

    @Singleton @Component(modules = {RestServiceModule.class, TestProvidersModule.class, DeterministicRandomHelperModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent {
        void inject(QuizFragmentTest test);
    }

    @Override protected TestApplicationComponent createComponent() {
        return DaggerQuizFragmentTest_TestApplicationComponent.builder()
                .deterministicRandomHelperModule(new DeterministicRandomHelperModule(1, 0))
                .build();
    }
}