package com.jbrunton.pockettimeline.entities.data;

import com.google.common.collect.FluentIterable;
import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.Collections;
import java.util.List;

import rx.Observable;

public class Repository<T extends Resource> {
    private List<T> resources = Collections.emptyList();

    public Observable<List<T>> all() {
        return Observable.just(resources);
    }

    public void set(List<T> resources) {
        this.resources = Collections.unmodifiableList(resources);
    }

    public Observable<Resource> find(String id) {
        T resource = FluentIterable.from(resources)
                .firstMatch(r -> r.getId().equals(id))
                .get();
        return Observable.just(resource);
    }
}
