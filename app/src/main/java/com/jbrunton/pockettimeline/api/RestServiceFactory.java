package com.jbrunton.pockettimeline.api;

import retrofit.RestAdapter;

public class RestServiceFactory {
    public static RestService create() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://timeline-pocketlearningapps.herokuapp.com/")
                .build();

        return restAdapter.create(RestService.class);
    }
}
