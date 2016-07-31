package com.jbrunton.pockettimeline.api.repositories;

import com.jbrunton.pockettimeline.api.repositories.http.HttpEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.http.HttpTimelinesRepository;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.helpers.CrashlyticsHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {
    @Singleton @Provides EventsRepository provideEventsRepository(RestService service, CrashlyticsHelper crashlyticsHelper) {
        return new HttpEventsRepository(service, crashlyticsHelper);
    }

    @Singleton @Provides TimelinesRepository provideTimelinesRepository(RestService service, CrashlyticsHelper crashlyticsHelper) {
        return new HttpTimelinesRepository(service, crashlyticsHelper);
    }
}
