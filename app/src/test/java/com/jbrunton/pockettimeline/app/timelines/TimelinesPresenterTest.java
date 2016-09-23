package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.entities.models.Timeline;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;
import com.jbrunton.pockettimeline.fixtures.TimelineFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static com.jbrunton.pockettimeline.fixtures.RepositoryFixtures.stub;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class TimelinesPresenterTest {
    static final Timeline TIMELINE = TimelineFactory.create();
    static final List<Timeline> TIMELINES = asList(TIMELINE);

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock TimelinesView view;
    @Mock TimelinesRepository repository;
    @Mock Navigator navigator;

    private TimelinesPresenter presenter;

    @Before public void setUp() {
        presenter = new TimelinesPresenter(repository, navigator, new TestSchedulerManager());
        presenter.bind(view);
        stub(repository).toReturn(TIMELINES);
    }

    @Test public void shouldShowLoadingIndicator() {
        presenter.onResume();
        verify(view).showLoadingIndicator();
    }

    @Test public void shouldRequestAndPresentTimelines() {
        presenter.onResume();

        verify(view).showTimelines(TIMELINES);
        verify(view).hideLoadingIndicator();
    }

    @Test public void shouldPresentMessageOnError() {
        stub(repository).toErrorWith(new Throwable("Message"));
        presenter.onResume();
        verify(view).showMessage("Error: Message");
    }

    @Test public void shouldShowTimelineActivity() {
        presenter.showTimelineDetails(TIMELINE);
        verify(navigator).startTimelineActivity(TIMELINE.getId());
    }
}