package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.api.EventsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestRepositoriesModule {
    @Singleton @Provides EventsRepository provideEventsRepository() {
        return mock(EventsRepository.class);
    }
}