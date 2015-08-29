package com.jbrunton.pockettimeline.api;

import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.api.resources.TimelineResource;
import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class EventsProvider {
    @Inject RestService service;

    @Inject public EventsProvider() {

    }

    public Observable<List<Event>> getEvents(String timelineId) {
        return service.getEvents(timelineId)
                .flatMap(Observable::from)
                .map(EventResource::toModel)
                .toList();
    }
}
