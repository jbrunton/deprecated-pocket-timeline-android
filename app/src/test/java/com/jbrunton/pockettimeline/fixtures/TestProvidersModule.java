package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.ProvidersModule;
import com.jbrunton.pockettimeline.api.service.RestService;

import static org.mockito.Mockito.mock;

public class TestProvidersModule extends ProvidersModule {
    @Override public EventsProvider eventsProvider(RestService service) {
        return mock(EventsProvider.class);
    }
}
