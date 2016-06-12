package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.api.HttpTimelineEventsRepository;
import com.jbrunton.pockettimeline.api.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.service.RestService;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class AddEventActivityModule {
    private final AddEventActivity activity;

    public AddEventActivityModule(AddEventActivity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity TimelineEventsRepository provideRepository(RestService service) {
        return new HttpTimelineEventsRepository(activity.getTimelineId(), service);
    }
}
