package com.jbrunton.pockettimeline.entities.data;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public class Repository<T extends Resource> {
    private ReplaySubject<List<T>> subject = ReplaySubject.createWithSize(1);

    public Repository() {
        set(Collections.<T>emptyList());
    }

    public Observable<List<T>> all() {
        return subject.asObservable();
    }

    public void set(List<T> resources) {
        subject.onNext(Collections.unmodifiableList(resources));
    }

    public Observable<Optional<T>> find(String id) {
        return all().map(resources -> {
            return FluentIterable.from(resources)
                    .firstMatch(r -> r.getId().equals(id));
        });
    }
}
