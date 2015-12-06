package com.jbrunton.pockettimeline.app.quiz;

import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.fixtures.DeterministicRandomHelper;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuizPresenterTest {
    private QuizPresenter presenter;
    private EventsProvider provider;
    private QuizView view;

    private RandomHelper randomHelper;

    final Event EVENT_ONE = new Event("1", new LocalDate(2014, DateTimeConstants.JUNE, 3), "Event One", null);
    final Event EVENT_TWO = new Event("2", new LocalDate(2015, DateTimeConstants.JUNE, 4), "Event Two", null);
    final List<Event> EVENTS = asList(EVENT_ONE, EVENT_TWO);

    @Before public void setUp() {
        randomHelper = new DeterministicRandomHelper(asList(1, 0));

        provider = mock(EventsProvider.class);
        view = mock(QuizView.class);

        presenter = new QuizPresenter(provider, new TestSchedulerManager(), randomHelper);
        presenter.bind(view);

        stubProviderToReturn(EVENTS);
    }

    @Test public void shouldRequestEvents() {
        presenter.onResume();
        verify(view).displayEvent(EVENT_TWO);
    }

    @Test public void shouldRespondToCorrectAnswer() {
        presenter.onResume();
        submitAnswerFor(EVENT_TWO);
        verify(view).showCorrectDialog();
    }

    @Test public void shouldRespondToIncorrectAnswer() {
        final String correctAnswer = "2015";
        presenter.onResume();

        submitAnswerFor(EVENT_ONE);

        verify(view).showIncorrectDialog(correctAnswer);
    }

    @Test public void shouldAskNextQuestion() {
        presenter.onResume();
        presenter.nextQuestion();
        verify(view).displayEvent(EVENT_ONE);
    }

    private void submitAnswerFor(Event event) {
        presenter.submitAnswer(Integer.toString(event.getDate().getYear()));
    }

    private void stubProviderToReturn(List<Event> events) {
        when(provider.getEvents()).thenReturn(Observable.just(events));
    }
}