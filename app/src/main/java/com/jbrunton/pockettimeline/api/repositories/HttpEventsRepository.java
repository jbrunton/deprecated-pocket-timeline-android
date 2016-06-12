package com.jbrunton.pockettimeline.api.repositories;

import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.entities.data.BaseReadableRepository;
import com.jbrunton.pockettimeline.entities.models.Event;

import java.util.List;

import rx.Observable;

public class HttpEventsRepository extends BaseReadableRepository<Event> implements EventsRepository {
    private final RestService service;

    public HttpEventsRepository(RestService service) {
        this.service = service;
    }

    @Override public Observable<List<Event>> all() {
        return createModels(service.getEvents());
    }

    @Override public Observable<List<Event>> search(String query) {
        return createModels(service.searchEvents(query));
    }

    private Observable<List<Event>> createModels(Observable<List<EventResource>> resources) {
        return resources.flatMap(Observable::from)
                .map(EventResource::toModel)
                .toList();
    }
}
