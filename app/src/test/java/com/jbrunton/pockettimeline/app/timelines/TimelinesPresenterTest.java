package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.models.Timeline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Time;
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

        presenter = new TimelinesPresenter(provider);
        presenter.bind(view);
    }

    @Test public void shouldBindToView() {
        assertThat(presenter.getView()).isSameAs(view);
    }

    @Test public void shouldDetachFromView() {
        presenter.detach();
        assertThat(presenter.getView()).isNull();
    }

    @Test public void shouldShowLoadingIndicator() {
        stubProviderWithEmptySequence();
        presenter.onResume();
        verify(view).showLoadingIndicator();
    }

    @Test public void shouldRequestTimelines() {
        stubProviderToReturn(TIMELINES);

        presenter.onResume();

        verify(view).showTimelines(TIMELINES);
        verify(view).hideLoadingIndicator();
    }

    private void stubProviderWithEmptySequence() {
        when(provider.getTimelines()).thenReturn(Observable.from(emptyList()));
    }

    private void stubProviderToReturn(List<Timeline> timelines) {
        when(provider.getTimelines()).thenReturn(Observable.just(timelines));
    }
}