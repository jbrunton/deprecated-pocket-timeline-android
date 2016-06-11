package com.jbrunton.pockettimeline.app;

import android.app.Application;

import com.jbrunton.pockettimeline.PocketTimelineApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    PocketTimelineApplication application;

    public ApplicationModule(PocketTimelineApplication application) {
        this.application = application;
    }

    @Provides @Singleton PocketTimelineApplication providesApplication() {
        return application;
    }
}
