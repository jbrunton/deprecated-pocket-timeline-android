package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.quiz.QuizPresenter;
import com.jbrunton.pockettimeline.app.shared.RxCache;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.app.timelines.TimelinesPresenter;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    PocketTimelineApplication application;

    public ApplicationModule(PocketTimelineApplication application) {
        this.application = application;
    }

    @Provides @Singleton protected RxCache provideRxCache() {
        return new RxCache();
    }

    @Provides @Singleton protected PocketTimelineApplication providesApplication() {
        return application;
    }

    @Provides @Singleton protected TimelinesPresenter provideTimelinesPresenter(TimelinesRepository repository, SchedulerManager schedulerManager) {
        return new TimelinesPresenter(repository, schedulerManager);
    }

    @Provides @Singleton protected QuizPresenter provideQuizPresenter(EventsRepository repository, SchedulerManager schedulerManager, RandomHelper randomHelper) {
        return new QuizPresenter(repository, schedulerManager, randomHelper);
    }
}
