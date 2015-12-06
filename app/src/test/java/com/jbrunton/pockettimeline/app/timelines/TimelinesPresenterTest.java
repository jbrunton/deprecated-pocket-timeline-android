package com.jbrunton.pockettimeline.app.timelines;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TimelinesPresenterTest {
    private TimelinesPresenter presenter;
    private TimelinesView view;

    @Before public void setUp() {
        presenter = new TimelinesPresenter();
        view = mock(TimelinesView.class);
    }

    @Test public void shouldBindToView() {
        presenter.bind(view);
        assertThat(presenter.getView()).isSameAs(view);
    }

    @Test public void shouldDetachFromView() {
        presenter.bind(view);
        assertThat(presenter.getView()).isNotNull();

        presenter.detach();

        assertThat(presenter.getView()).isNull();
    }
}