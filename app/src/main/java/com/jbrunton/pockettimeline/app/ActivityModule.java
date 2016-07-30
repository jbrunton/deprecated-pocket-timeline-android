package com.jbrunton.pockettimeline.app;

import android.app.Activity;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.quiz.QuizPresenter;
import com.jbrunton.pockettimeline.app.search.SearchPresenter;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.app.timelines.TimelinesPresenter;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity protected Activity provideActivity() {
        return activity;
    }

    @Provides @PerActivity protected Navigator provideNavigator() {
        return new Navigator(activity);
    }

    @Provides @PerActivity protected TimelinesPresenter provideTimelinesPresenter(TimelinesRepository repository, Navigator navigator, SchedulerManager schedulerManager) {
        return new TimelinesPresenter(repository, navigator, schedulerManager);
    }

    @Provides @PerActivity protected QuizPresenter provideQuizPresenter(EventsRepository repository, SchedulerManager schedulerManager, RandomHelper randomHelper) {
        return new QuizPresenter(repository, schedulerManager, randomHelper);
    }

    @Provides @PerActivity protected SearchPresenter provideSearchPresenter(EventsRepository repository, SchedulerManager schedulerManager) {
        return new SearchPresenter(repository, schedulerManager);
    }
}
