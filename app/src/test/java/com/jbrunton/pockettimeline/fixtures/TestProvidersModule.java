package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.ProvidersModule;
import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.api.service.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestProvidersModule {
    @Singleton @Provides EventsProvider eventsProvider() {
        return mock(EventsProvider.class);
    }

    @Singleton @Provides TimelinesProvider timelinesProvider() {
        return mock(TimelinesProvider.class);
    }
}
