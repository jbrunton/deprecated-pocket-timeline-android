package com.jbrunton.pockettimeline.api.service;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class RestServiceModule {
    @Provides RestService provideRestService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://timeline-pocketlearningapps.herokuapp.com/")
                .build();

        return restAdapter.create(RestService.class);
    }
}
