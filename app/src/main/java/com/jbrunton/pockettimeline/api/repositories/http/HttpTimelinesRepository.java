package com.jbrunton.pockettimeline.api.repositories.http;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.api.resources.TimelineResource;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.entities.data.BaseReadableRepository;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

import rx.Observable;

public class HttpTimelinesRepository extends BaseReadableRepository<Timeline> implements TimelinesRepository {
    private final RestService service;

    public HttpTimelinesRepository(RestService service) {
        this.service = service;
    }

    @Override public Observable<List<Timeline>> all() {
        return createModels(service.getTimelines());
    }

    private Observable<List<Timeline>> createModels(Observable<List<TimelineResource>> resources) {
        return resources.flatMap(Observable::from)
                .map(TimelineResource::toModel)
                .toList();
    }
}
