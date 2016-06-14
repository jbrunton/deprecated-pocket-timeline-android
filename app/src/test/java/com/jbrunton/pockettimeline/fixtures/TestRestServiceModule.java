package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.api.service.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestRestServiceModule {
    @Singleton @Provides protected RestService provideRestService() {
        return mock(RestService.class);
    }
}
