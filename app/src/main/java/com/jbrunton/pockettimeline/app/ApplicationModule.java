package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.shared.RxCache;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.app.timelines.TimelinesPresenter;

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

    @Provides @Singleton TimelinesPresenter provideTimelinesPresenter(TimelinesRepository repository, SchedulerManager schedulerManager) {
        return new TimelinesPresenter(repository, schedulerManager);
    }
}
