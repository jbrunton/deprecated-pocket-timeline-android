package com.jbrunton.pockettimeline.entities.data;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public class Repository<T extends Resource> {
    private ReplaySubject<List<T>> subject = ReplaySubject.createWithSize(1);

    public Repository() {
        set(defaultValues());
    }

    public Observable<List<T>> all() {
        return subject.asObservable();
    }

    public void set(List<T> resources) {
        set(Observable.just(resources));
    }

    public void set(Observable<List<T>> observable) {
        observable.subscribe(new Observer<List<T>>() {
            @Override public void onCompleted() {
            }

            @Override public void onError(Throwable e) {
                subject.onError(e);
            }

            @Override public void onNext(List<T> resources) {
                subject.onNext(Collections.unmodifiableList((resources)));
            }
        });
    }

    public Observable<Optional<T>> find(String id) {
        return all().map(resources -> {
            return FluentIterable.from(resources)
                    .firstMatch(r -> r.getId().equals(id));
        });
    }

    protected Observable<List<T>> defaultValues() {
        return Observable.just(Collections.<T>emptyList());
    }
}
