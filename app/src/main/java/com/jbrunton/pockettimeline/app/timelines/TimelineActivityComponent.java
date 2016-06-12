package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.app.ActivityModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {
        TimelineModule.class,
        ActivityModule.class
})
public interface TimelineActivityComponent {
    void inject(AddEventActivity activity);
    void inject(TimelineActivity activity);
}
