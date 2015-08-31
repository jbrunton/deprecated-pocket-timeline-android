package com.jbrunton.pockettimeline.api.providers;

import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.api.resources.TimelineResource;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class TimelinesProvider {
    private final RestService service;

    @Inject public TimelinesProvider(RestService service) {
        this.service = service;
    }

    public Observable<List<Timeline>> getTimelines() {
        return service.getTimelines()
                .flatMap(Observable::from)
                .map(TimelineResource::toModel)
                .toList();
    }

    public Observable<Timeline> getTimeline(String timelineId) {
        return service.getTimeline(timelineId)
                .map(TimelineResource::toModel);
    }
}
