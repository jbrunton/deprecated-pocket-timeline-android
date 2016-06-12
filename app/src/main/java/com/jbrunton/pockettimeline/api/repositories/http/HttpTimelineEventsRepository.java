package com.jbrunton.pockettimeline.api.repositories.http;

import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.resources.EventRequest;
import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.entities.data.BaseWritableRepository;
import com.jbrunton.pockettimeline.entities.models.Event;

import java.util.List;

import rx.Observable;

public class HttpTimelineEventsRepository extends BaseWritableRepository<Event> implements TimelineEventsRepository {
    private final String timelineId;
    private final RestService service;

    public HttpTimelineEventsRepository(String timelineId, RestService service) {
        this.timelineId = timelineId;
        this.service = service;
    }

    @Override public Observable<List<Event>> all() {
        return createModels(service.getEvents(timelineId));
    }

    @Override protected Observable<Event> create(Event resource) {
        EventRequest request = new EventRequest(resource.getDate(), resource.getTitle());
        return service.createEvent(timelineId, request)
                .map(EventResource::toModel);
    }

    @Override protected Observable<Event> update(Event resource) {
        return null;
    }

    @Override public Observable<Void> delete(String id) {
        return service.deleteEvent(id)
                .map(response -> null);
    }

    private Observable<List<Event>> createModels(Observable<List<EventResource>> resources) {
        return resources.flatMap(Observable::from)
                .map(EventResource::toModel)
                .toList();
    }
}
