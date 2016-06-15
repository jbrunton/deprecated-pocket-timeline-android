package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.app.ActivityModule;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {
        TimelineModule.class,
        ActivityModule.class
})
public interface TimelineActivityComponent {
    void inject(AddEventActivity activity);
    void inject(TimelineActivity activity);
}
