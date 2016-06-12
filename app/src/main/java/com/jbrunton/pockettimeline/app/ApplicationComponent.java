package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.api.repositories.RepositoriesModule;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.quiz.QuizFragment;
import com.jbrunton.pockettimeline.app.search.SearchFragment;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        RepositoriesModule.class,
        ApplicationModule.class,
        RestServiceModule.class
})
public interface ApplicationComponent {
    void inject(TimelinesFragment fragment);
    void inject(QuizFragment fragment);
    void inject(SearchFragment fragment);

    RestService restService();
    EventsRepository eventsRepository();
    TimelinesRepository timelinesRepository();
}
