package com.jbrunton.pockettimeline.api.repositories;

import com.jbrunton.pockettimeline.api.repositories.http.HttpEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.http.HttpTimelinesRepository;
import com.jbrunton.pockettimeline.api.service.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {
    @Singleton @Provides EventsRepository provideEventsRepository(RestService service) {
        return new HttpEventsRepository(service);
    }

    @Singleton @Provides TimelinesRepository provideTimelinesRepository(RestService service) {
        return new HttpTimelinesRepository(service);
    }
}
