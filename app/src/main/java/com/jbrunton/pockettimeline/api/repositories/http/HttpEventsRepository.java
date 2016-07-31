package com.jbrunton.pockettimeline.api.repositories.http;

import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.entities.data.BaseReadableRepository;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.helpers.CrashlyticsHelper;

import java.util.List;

import rx.Observable;

public class HttpEventsRepository extends BaseReadableRepository<Event> implements EventsRepository {
    private final RestService service;
    private final CrashlyticsHelper crashlyticsHelper;

    public HttpEventsRepository(RestService service, CrashlyticsHelper crashlyticsHelper) {
        this.service = service;
        this.crashlyticsHelper = crashlyticsHelper;
    }

    @Override public Observable<List<Event>> all() {
        return service.getEvents().map(crashlyticsHelper::tryInstantiateAll);
    }

    @Override public Observable<List<Event>> search(String query) {
        return service.searchEvents(query).map(crashlyticsHelper::tryInstantiateAll);
    }
}
