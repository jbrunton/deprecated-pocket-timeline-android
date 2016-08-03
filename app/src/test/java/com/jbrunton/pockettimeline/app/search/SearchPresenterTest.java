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

import static com.jbrunton.pockettimeline.fixtures.RepositoryFixtures.stubSearch;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class SearchPresenterTest {
    static final String QUERY = "query";
    static final List<Event> RESULTS = asList(
            EventFactory.create(),
            EventFactory.create());

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock EventsRepository repository;
    @Mock SearchView view;

    private SearchPresenter presenter;

    @Before public void setUp() {
        presenter = new SearchPresenter(repository, new TestSchedulerManager());
        presenter.bind(view);
    }

    @Test public void shouldShowLoadingIndicator() {
        stubSearch(repository, QUERY).toReturn(RESULTS);
        presenter.performSearch(QUERY);
        verify(view).showLoadingIndicator();
    }

    @Test public void shouldRequestAndPresentResults() {
        stubSearch(repository, QUERY).toReturn(RESULTS);

        presenter.performSearch(QUERY);

        verify(view).showResults(RESULTS);
        verify(view).hideLoadingIndicator();
    }

    @Test public void shouldPresentMessageOnError() {
        stubSearch(repository, QUERY).toErrorWith(new Throwable("Message"));
        presenter.performSearch(QUERY);
        verify(view).showMessage("Error: Message");
    }
}