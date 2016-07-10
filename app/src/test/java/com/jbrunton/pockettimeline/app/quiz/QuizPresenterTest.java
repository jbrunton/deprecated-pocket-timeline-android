package com.jbrunton.pockettimeline.app.quiz;

import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.fixtures.DeterministicRandomHelper;
import com.jbrunton.pockettimeline.fixtures.EventFactory;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import rx.Observable;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuizPresenterTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock EventsRepository repository;
    @Mock QuizView view;
    RandomHelper randomHelper;
    QuizPresenter presenter;

    private static final Event EVENT_ONE = EventFactory.create();
    private static final Event EVENT_TWO = EventFactory.create();
    private static final List<Event> EVENTS = asList(EVENT_ONE, EVENT_TWO);

    @Before public void setUp() {
        randomHelper = new DeterministicRandomHelper(asList(1, 0));
        presenter = new QuizPresenter(repository, new TestSchedulerManager(), randomHelper);
        stubRepositoryToReturn(EVENTS);

        presenter.bind(view);
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
        final String correctAnswer = answerFor(EVENT_TWO);
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
        presenter.submitAnswer(answerFor(event));
    }

    private String answerFor(Event event) {
        return Integer.toString(event.getDate().getYear());
    }

    private void stubRepositoryToReturn(List<Event> events) {
        when(repository.all()).thenReturn(Observable.just(events));
    }
}