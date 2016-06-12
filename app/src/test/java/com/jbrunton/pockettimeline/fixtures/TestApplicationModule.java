package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.app.timelines.TimelinesPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Module
public class TestApplicationModule {
    @Provides @Singleton Navigator provideNavigator() {
        return spy(Navigator.class);
    }

    @Provides @Singleton TimelinesPresenter provideTimelinesPresenter() {
        return mock(TimelinesPresenter.class);
    }
}
