package com.jbrunton.pockettimeline.entities.data;

import com.google.common.collect.FluentIterable;
import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.List;

import rx.Observable;

public abstract class BaseRepository<T extends Resource> implements Repository<T> {
    @Override public Observable<T> find(String id) {
        return all().map(finderFor(id)::find);
    }

    private Finder finderFor(String id) {
        return new Finder(id);
    }

    private class Finder {
        private final String id;

        private Finder(String id) {
            this.id = id;
        }

        public T find(List<T> resources) {
            return FluentIterable.from(resources)
                    .firstMatch(r -> r.getId().equals(id))
                    .get();
        }
    }
}
