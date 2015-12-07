package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.api.RepositoriesModule;
import com.jbrunton.pockettimeline.api.providers.ProvidersModule;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.quiz.QuizFragment;
import com.jbrunton.pockettimeline.app.search.SearchFragment;
import com.jbrunton.pockettimeline.app.timelines.AddEventActivity;
import com.jbrunton.pockettimeline.app.timelines.TimelineActivity;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RestServiceModule.class, ProvidersModule.class, RepositoriesModule.class})
public interface ApplicationComponent {
    void inject(TimelineActivity activity);
    void inject(AddEventActivity activity);

    void inject(TimelinesFragment fragment);
    void inject(QuizFragment fragment);
    void inject(SearchFragment fragment);
}
