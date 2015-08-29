package com.jbrunton.pockettimeline.api;

import com.jbrunton.pockettimeline.api.resources.TimelineResource;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

public class TimelinesProvider {
    @Inject RestService service;

    @Inject
    public TimelinesProvider() {

    }

    public Observable<List<Timeline>> getTimelines() {
        return service.getTimelines()
                .flatMap(new Func1<List<TimelineResource>, Observable<TimelineResource>>() {
                    @Override
                    public Observable<TimelineResource> call(List<TimelineResource> timelineResources) {
                        return Observable.from(timelineResources);
                    }
                })
                .map(new Func1<TimelineResource, Timeline>() {
                    @Override
                    public Timeline call(TimelineResource resource) {
                        return new Timeline(resource.getId(), resource.getTitle(), resource.getDescription());
                    }
                }).toList();
    }
}
