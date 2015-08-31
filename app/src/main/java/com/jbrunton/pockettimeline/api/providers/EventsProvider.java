package com.jbrunton.pockettimeline.api.providers;

import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.models.Event;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class EventsProvider {
    @Inject RestService service;

    @Inject public EventsProvider() {

    }

    public Observable<List<Event>> getEvents(String timelineId) {
        return createModels(service.getEvents(timelineId));
    }

    public Observable<List<Event>> getEvents() {
        return createModels(service.getEvents());
    }

    private Observable<List<Event>> createModels(Observable<List<EventResource>> resources) {
        return resources.flatMap(Observable::from)
                .map(EventResource::toModel)
                .toList();
    }
}
