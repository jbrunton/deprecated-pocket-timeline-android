package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    PocketTimelineApplication application;

    public ApplicationModule(PocketTimelineApplication application) {
        this.application = application;
    }

    @Provides @Singleton protected SchedulerManager provideSchedulerManager() {
        return new SchedulerManager();
    }

    @Provides @Singleton protected PocketTimelineApplication providesApplication() {
        return application;
    }
}
