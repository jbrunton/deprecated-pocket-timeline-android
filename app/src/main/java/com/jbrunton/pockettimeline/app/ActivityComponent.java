package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.app.quiz.QuizFragment;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(TimelinesFragment fragment);
    void inject(QuizFragment fragment);
}
