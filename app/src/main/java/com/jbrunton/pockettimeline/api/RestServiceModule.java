package com.jbrunton.pockettimeline.api;

import dagger.Module;
import dagger.Provides;

@Module
public class RestServiceModule {
    @Provides RestService provideRestService() {
        return RestServiceFactory.create();
    }
}
