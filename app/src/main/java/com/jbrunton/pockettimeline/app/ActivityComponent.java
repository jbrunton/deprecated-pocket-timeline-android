package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(TimelinesFragment fragment);
    Navigator navigator();
}
