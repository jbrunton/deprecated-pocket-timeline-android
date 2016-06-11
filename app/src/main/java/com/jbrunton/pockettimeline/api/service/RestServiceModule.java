package com.jbrunton.pockettimeline.api.service;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jbrunton.pockettimeline.PocketTimelineApplication;

import org.joda.time.LocalDate;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestServiceModule {
    private final String baseUrl;
    private static final int TEN_MEGABYTES = 10 * 1024 * 1024;

    public RestServiceModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides RestService provideRestService(PocketTimelineApplication application) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(createClient(application))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(RestService.class);
    }

    @NonNull private OkHttpClient createClient(final PocketTimelineApplication application) {
        return new OkHttpClient.Builder()
                .cache(new Cache(application.getCacheDir(), TEN_MEGABYTES))
                .addInterceptor(new CachingInterceptor(application))
                .build();
    }

}
