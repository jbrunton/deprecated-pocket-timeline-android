package com.jbrunton.pockettimeline.app.quiz;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.app.ApplicationComponent;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = QuizFragmentModule.class)
public interface QuizFragmentComponent {
    void inject(QuizFragment fragment);
}
