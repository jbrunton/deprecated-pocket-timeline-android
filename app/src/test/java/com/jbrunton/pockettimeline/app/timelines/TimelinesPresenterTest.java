package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.entities.models.Timeline;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import rx.Observable;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TimelinesPresenterTest {
    private TimelinesPresenter presenter;
    private TimelinesView view;
    private TimelinesProvider provider;

    private final List<Timeline> TIMELINES = asList(
            new Timeline("1", "Some Timeline", "Some description")
    );

    @Before public void setUp() {
        view = mock(TimelinesView.class);
        provider = mock(TimelinesProvider.class);

        presenter = new TimelinesPresenter(provider, new TestSchedulerManager());
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

    private void stubProviderToErrorWith(Throwable throwable) {
        when(provider.getTimelines()).thenReturn(Observable.error(throwable));
    }

    private void stubProviderWithEmptySequence() {
        when(provider.getTimelines()).thenReturn(Observable.from(emptyList()));
    }

    private void stubProviderToReturn(List<Timeline> timelines) {
        when(provider.getTimelines()).thenReturn(Observable.just(timelines));
    }
}