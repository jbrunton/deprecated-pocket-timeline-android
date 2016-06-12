package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.http.HttpTimelineEventsRepository;
import com.jbrunton.pockettimeline.api.service.RestService;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class TimelineModule {
    private final String timelineId;

    public TimelineModule(String timelineId) {
        this.timelineId = timelineId;
    }

    @Provides @PerActivity TimelineEventsRepository provideRepository(RestService service) {
        return new HttpTimelineEventsRepository(timelineId, service);
    }
}
