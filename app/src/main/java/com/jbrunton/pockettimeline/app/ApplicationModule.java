package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.shared.RxCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

    @Provides @Singleton Navigator provideNavigator() {
        return new Navigator();
    }
}
