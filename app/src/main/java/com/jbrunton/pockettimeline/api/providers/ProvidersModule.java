package com.jbrunton.pockettimeline.api.providers;

import com.jbrunton.pockettimeline.api.service.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ProvidersModule {
    @Singleton @Provides EventsProvider eventsProvider(RestService service) {
        return new EventsProvider(service);
    }
}
