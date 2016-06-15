package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.entities.models.Timeline;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import rx.Observable;

import static java.util.Collections.emptyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TimelinePresenterTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    private TimelinePresenter presenter;

    @Mock TimelineView view;
    @Mock TimelinesRepository timelinesRepository;
    @Mock TimelineEventsRepository eventsRepository;
    @Mock Navigator navigator;

    private final Timeline TIMELINE = new Timeline("1", "Some Timeline", "Some description");

    @Before public void setUp() {
        presenter = new TimelinePresenter(timelinesRepository, eventsRepository, navigator, new TestSchedulerManager());
        presenter.bind(view);

        stubTimelinesRepositoryToFind(TIMELINE).forId(TIMELINE.getId());
        when(eventsRepository.timelineId()).thenReturn(TIMELINE.getId());
        stubEventsRepositoryWithEmptySequence();
    }

    @Test public void shouldShowLoadingIndicator() {
        presenter.onResume();
        verify(view).showLoadingIndicator();
    }

    @Ignore @Test public void shouldRequestAndPresentTimelines() {
        presenter.onResume();

        // TODO: need a different matcher, as we create a new instance using Timeline::withEvents
        verify(view).showTimeline(TIMELINE);
        verify(view).hideLoadingIndicator();
    }

    @Test public void shouldPresentMessageOnTimelinesRepositoryError() {
        stubTimelinesProviderToErrorWith(new Throwable("Message"));
        presenter.onResume();
        verify(view).showMessage("Error: Message");
    }

    @Test public void shouldPresentMessageOnEventsRepositoryError() {
        stubEventsProviderToErrorWith(new Throwable("Message"));
        presenter.onResume();
        verify(view).showMessage("Error: Message");
    }
    
    private class TimelineDsl {
        private final Timeline timeline;

        public TimelineDsl(Timeline timeline) {
            this.timeline = timeline;
        }

        public void forId(String timelineId) {
            when(timelinesRepository.find(timelineId)).thenReturn(Observable.just(timeline));
        }
    }

    private void stubTimelinesProviderToErrorWith(Throwable throwable) {
        when(timelinesRepository.find(anyString())).thenReturn(Observable.error(throwable));
    }

    private TimelineDsl stubTimelinesRepositoryToFind(Timeline timeline) {
        return new TimelineDsl(timeline);
    }

    private void stubEventsProviderToErrorWith(Throwable throwable) {
        when(eventsRepository.all()).thenReturn(Observable.error(throwable));
    }

    private void stubEventsRepositoryWithEmptySequence() {
        when(eventsRepository.all()).thenReturn(Observable.just(emptyList()));
    }
}
