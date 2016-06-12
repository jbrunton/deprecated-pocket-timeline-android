package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.api.repositories.HttpTimelineEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.service.RestService;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class TimelineActivityModule {
    private final TimelineActivity activity;

    public TimelineActivityModule(TimelineActivity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity TimelineEventsRepository provideRepository(RestService service) {
        return new HttpTimelineEventsRepository(activity.getTimelineId(), service);
    }
}
