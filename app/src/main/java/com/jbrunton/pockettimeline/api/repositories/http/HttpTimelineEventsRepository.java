package com.jbrunton.pockettimeline.api.repositories.http;

import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.resources.EventRequest;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.entities.data.BaseWritableRepository;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.helpers.CrashlyticsHelper;

import java.util.List;

import rx.Observable;

public class HttpTimelineEventsRepository extends BaseWritableRepository<Event> implements TimelineEventsRepository {
    private final String timelineId;
    private final RestService service;
    private final CrashlyticsHelper crashlyticsHelper;

    public HttpTimelineEventsRepository(String timelineId, RestService service, CrashlyticsHelper crashlyticsHelper) {
        this.timelineId = timelineId;
        this.service = service;
        this.crashlyticsHelper = crashlyticsHelper;
    }

    @Override public Observable<List<Event>> all() {
        return service.getEvents(timelineId).map(crashlyticsHelper::tryInstantiateAll);
    }

    @Override protected Observable<Event> create(Event resource) {
        EventRequest request = new EventRequest(resource.getDate(), resource.getTitle());
        return service.createEvent(timelineId, request)
                .map(crashlyticsHelper::instantiate);
    }

    @Override protected Observable<Event> update(Event resource) {
        return null;
    }

    @Override public Observable<Void> delete(String id) {
        return service.deleteEvent(id)
                .map(response -> null);
    }

    @Override public String timelineId() {
        return timelineId;
    }
}
