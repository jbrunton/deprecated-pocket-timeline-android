package com.jbrunton.pockettimeline.app.timelines;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class TimelinesPresenterTest {
    private TimelinesPresenter presenter;
    private TimelinesView view;

    @Before public void setUp() {
        presenter = new TimelinesPresenter();
        view = mock(TimelinesView.class);
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
        presenter.onResume();
        verify(view).showLoadingIndicator();
    }
}