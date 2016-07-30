package com.jbrunton.pockettimeline.app.search;

import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.fixtures.EventFactory;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import rx.Observable;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SearchPresenterTest {
    public static final String QUERY = "query";
    public static final List<Event> RESULTS = asList(
            EventFactory.create(),
            EventFactory.create());

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock EventsRepository repository;
    @Mock SearchView view;

    private SearchPresenter presenter;

    @Before public void setUp() {
        presenter = new SearchPresenter(repository, new TestSchedulerManager());
        presenter.bind(view);

        when(repository.search(QUERY)).thenReturn(Observable.just(RESULTS));
    }

    @Test public void shouldRequestAndPresentResults() {
        presenter.performSearch(QUERY);
        verify(view).showResults(RESULTS);
    }

    @Test public void shouldPresentMessageOnError() {
        when(repository.search(QUERY)).thenReturn(Observable.error(new Throwable("Message")));
        presenter.performSearch(QUERY);
        verify(view).showMessage("Error: Message");
    }
}