package com.jbrunton.pockettimeline.app.quiz;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class QuizFragmentModule {
    @Provides @PerActivity QuizPresenter providePresenter(EventsRepository repository, SchedulerManager schedulerManager, RandomHelper randomHelper) {
        return new QuizPresenter(repository, schedulerManager, randomHelper);
    }
}
