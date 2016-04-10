package com.jbrunton.pockettimeline.api;

import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.entities.data.RxRepository;
import com.jbrunton.pockettimeline.entities.models.Event;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

public class HttpEventsRepository extends RxRepository<Event> implements EventsRepository {
    private final RestService service;

    public HttpEventsRepository(RestService service) {
        this.service = service;
    }

    @Override protected Observable<List<Event>> defaultValues() {
        return service.getEvents().flatMap(Observable::from)
                .map(EventResource::toModel)
                .toList();
    }
}
