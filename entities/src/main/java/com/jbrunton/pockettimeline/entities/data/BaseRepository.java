package com.jbrunton.pockettimeline.entities.data;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.List;

import rx.Observable;

public abstract class BaseRepository<T extends Resource> implements Repository<T> {
    @Override public Observable<T> find(String id) {
        return all().map(resources -> {
            return FluentIterable.from(resources)
                    .firstMatch(r -> r.getId().equals(id))
                    .get();
        });
    }
}
