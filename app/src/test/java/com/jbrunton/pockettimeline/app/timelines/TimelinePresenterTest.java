package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.entities.models.Timeline;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;
import com.jbrunton.pockettimeline.fixtures.TimelineFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.jbrunton.pockettimeline.fixtures.RepositoryFixtures.stub;
import static com.jbrunton.pockettimeline.fixtures.RepositoryFixtures.stubFind;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class TimelinePresenterTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock TimelineView view;
    @Mock TimelinesRepository timelinesRepository;
    @Mock TimelineEventsRepository eventsRepository;
    @Mock Navigator navigator;

    private TimelinePresenter presenter;

    private static final Timeline TIMELINE = TimelineFactory.create();
    private static final String TIMELINE_ID = TIMELINE.getId();

    @Before public void setUp() {
        presenter = new TimelinePresenter(timelinesRepository, eventsRepository, navigator, new TestSchedulerManager());
        presenter.bind(view);

        stub(timelinesRepository).toReturn(singletonList(TIMELINE));
        stub(eventsRepository).toReturn(TIMELINE);
    }

    @Test public void shouldShowLoadingIndicator() {
        presenter.onResume();
        verify(view).showLoadingIndicator();
    }

    // Need a different matcher, as we create a new instance using Timeline::withEvents
    @Ignore @Test public void shouldRequestAndPresentTimelines() {
        presenter.onResume();

        verify(view).showTimeline(TIMELINE);
        verify(view).hideLoadingIndicator();
    }

    @Test public void shouldPresentMessageOnTimelinesRepositoryError() {
        stubFind(timelinesRepository, TIMELINE_ID).toErrorWith(new Throwable("Message"));
        presenter.onResume();
        verify(view).showMessage("Error: Message");
    }

    @Test public void shouldPresentMessageOnEventsRepositoryError() {
        stub(eventsRepository).toErrorWith(new Throwable("Message"));
        presenter.onResume();
        verify(view).showMessage("Error: Message");
    }
}
