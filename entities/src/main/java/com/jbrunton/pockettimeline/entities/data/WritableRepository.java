package com.jbrunton.pockettimeline.entities.data;

import com.jbrunton.pockettimeline.entities.models.Resource;

import rx.Observable;

public interface WritableRepository<T extends Resource> {
    Observable<T> save(T resource);
    Observable<Void> delete(String id);
}
