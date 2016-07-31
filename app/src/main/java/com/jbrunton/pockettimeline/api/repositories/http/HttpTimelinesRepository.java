package com.jbrunton.pockettimeline.api.repositories.http;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.entities.data.BaseReadableRepository;
import com.jbrunton.pockettimeline.entities.models.Timeline;
import com.jbrunton.pockettimeline.helpers.CrashlyticsHelper;

import java.util.List;

import rx.Observable;

public class HttpTimelinesRepository extends BaseReadableRepository<Timeline> implements TimelinesRepository {
    private final RestService service;
    private final CrashlyticsHelper crashlyticsHelper;

    public HttpTimelinesRepository(RestService service, CrashlyticsHelper crashlyticsHelper) {
        this.service = service;
        this.crashlyticsHelper = crashlyticsHelper;
    }

    @Override public Observable<List<Timeline>> all() {
        return service.getTimelines().map(crashlyticsHelper::tryInstantiateAll);
    }
}
