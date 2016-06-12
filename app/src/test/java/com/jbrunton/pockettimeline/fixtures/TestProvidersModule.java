package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestProvidersModule {
    @Singleton @Provides TimelinesProvider timelinesProvider() {
        return mock(TimelinesProvider.class);
    }
}
