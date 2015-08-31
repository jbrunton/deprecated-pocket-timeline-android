package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.quiz.QuizFragment;
import com.jbrunton.pockettimeline.app.timelines.TimelineFragment;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RestServiceModule.class)
public interface ApplicationComponent {
    void inject(TimelineFragment fragment);
    void inject(TimelinesFragment fragment);
    void inject(QuizFragment fragment);
}
