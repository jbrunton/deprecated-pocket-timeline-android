package com.jbrunton.pockettimeline.entities.data;

import com.jbrunton.pockettimeline.entities.models.Resource;

import rx.Observable;

public abstract class BaseWritableRepository<T extends Resource> extends BaseReadableRepository<T>
        implements WritableRepository<T>
{
    @Override public Observable<T> save(T resource) {
        if (resource.isNewResource()) {
            return create(resource);
        } else {
            return update(resource);
        }
    }

    protected abstract Observable<T> create(T resource);
    protected abstract Observable<T> update(T resource);
}
