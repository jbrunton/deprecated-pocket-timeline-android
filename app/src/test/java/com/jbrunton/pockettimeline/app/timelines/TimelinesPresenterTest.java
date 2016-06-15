package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.entities.models.Timeline;
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
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TimelinesPresenterTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private TimelinesPresenter presenter;

    @Mock TimelinesView view;
    @Mock TimelinesRepository repository;
    @Mock Navigator navigator;

    private final Timeline TIMELINE = new Timeline("1", "Some Timeline", "Some description");
    private final List<Timeline> TIMELINES = asList(TIMELINE);

    @Before public void setUp() {
        presenter = new TimelinesPresenter(repository, navigator, new TestSchedulerManager());
        presenter.bind(view);
    }

    @Test public void shouldShowLoadingIndicator() {
        stubProviderWithEmptySequence();
        presenter.onResume();
        verify(view).showLoadingIndicator();
    }

    @Test public void shouldRequestAndPresentTimelines() {
        stubProviderToReturn(TIMELINES);

        presenter.onResume();

        verify(view).showTimelines(TIMELINES);
        verify(view).hideLoadingIndicator();
    }

    @Test public void shouldPresentMessageOnError() {
        stubProviderToErrorWith(new Throwable("Message"));
        presenter.onResume();
        verify(view).showMessage("Error: Message");
    }

    @Test public void shouldShowTimelineActivity() {
        presenter.showTimelineDetails(TIMELINE);
        verify(navigator).startTimelineActivity(TIMELINE.getId());
    }

    private void stubProviderToErrorWith(Throwable throwable) {
        when(repository.all()).thenReturn(Observable.error(throwable));
    }

    private void stubProviderWithEmptySequence() {
        when(repository.all()).thenReturn(Observable.from(emptyList()));
    }

    private void stubProviderToReturn(List<Timeline> timelines) {
        when(repository.all()).thenReturn(Observable.just(timelines));
    }
}