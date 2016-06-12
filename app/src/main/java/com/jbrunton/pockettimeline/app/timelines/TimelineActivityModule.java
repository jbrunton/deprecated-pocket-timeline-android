package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.PerActivity;

import dagger.Module;

@PerActivity
@Module
public class TimelineActivityModule {
    private final TimelineActivity activity;

    public TimelineActivityModule(TimelineActivity activity) {
        this.activity = activity;
    }

//    @Provides @PerActivity TimelineEventsRepository provideRepository(RestService service) {
//        return new HttpTimelineEventsRepository(activity.getTimelineId(), service);
//    }
}
