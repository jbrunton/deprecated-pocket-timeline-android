package com.jbrunton.pockettimeline.api.repositories;

import com.jbrunton.pockettimeline.api.service.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {
    @Singleton @Provides public EventsRepository providesEventsRepository(RestService service) {
        return new HttpEventsRepository(service);
    }
}
