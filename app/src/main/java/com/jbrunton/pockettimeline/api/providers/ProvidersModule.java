package com.jbrunton.pockettimeline.api.providers;

import com.jbrunton.pockettimeline.api.service.RestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ProvidersModule {
    @Singleton @Provides TimelinesProvider timelinesProvider(RestService service) {
        return new TimelinesProvider(service);
    }
}
