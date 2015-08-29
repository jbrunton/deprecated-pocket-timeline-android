package com.jbrunton.pockettimeline.api;

import com.jbrunton.pockettimeline.api.resources.TimelineResource;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class TimelinesProvider {
    @Inject RestService service;

    @Inject
    public TimelinesProvider() {

    }

    public Observable<List<Timeline>> getTimelines() {
        return service.getTimelines()
                .flatMap(Observable::from)
                .map(TimelineResource::toModel)
                .toList();
    }

}
