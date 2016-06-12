package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.app.ApplicationComponent;

import dagger.Component;
import dagger.Subcomponent;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface TimelinesFragmentComponent {
    void inject(TimelinesFragment fragment);
}
