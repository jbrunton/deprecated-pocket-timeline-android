package com.jbrunton.pockettimeline.app.shared;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.timelines.TimelinesPresenter;
import com.jbrunton.pockettimeline.app.timelines.TimelinesView;
import com.jbrunton.pockettimeline.fixtures.TestSchedulerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class BasePresenterTest {
    private LoadingIndicatorView view;
    private BasePresenter<LoadingIndicatorView> presenter;

    @Before public void setUp() {
        view = mock(LoadingIndicatorView.class);

        presenter = new BasePresenter<>();
        presenter.bind(view);
    }

    @Test public void shouldBindToView() {
        assertThat(presenter.getView()).isSameAs(view);
    }

    @Test public void shouldDetachFromView() {
        presenter.detach();
        assertThat(presenter.getView()).isNull();
    }
}