package com.jbrunton.pockettimeline.app.quiz;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.api.EventsRepository;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class QuizFragmentModule {
    private final QuizFragment fragment;

    public QuizFragmentModule(QuizFragment fragment) {
        this.fragment = fragment;
    }

    @Provides @PerActivity QuizPresenter providePresenter(EventsRepository repository, SchedulerManager schedulerManager, RandomHelper randomHelper) {
        return new QuizPresenter(repository, schedulerManager, randomHelper);
    }
}
