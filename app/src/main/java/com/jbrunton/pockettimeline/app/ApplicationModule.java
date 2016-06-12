package com.jbrunton.pockettimeline.app;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.service.CachingInterceptor;
import com.jbrunton.pockettimeline.api.service.LocalDateAdapter;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.app.shared.RxCache;

import org.joda.time.LocalDate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    PocketTimelineApplication application;

    public ApplicationModule(PocketTimelineApplication application) {
        this.application = application;
    }

    @Provides @Singleton RxCache provideRxCache() {
        return new RxCache();
    }

    @Provides @Singleton PocketTimelineApplication providesApplication() {
        return application;
    }
}
