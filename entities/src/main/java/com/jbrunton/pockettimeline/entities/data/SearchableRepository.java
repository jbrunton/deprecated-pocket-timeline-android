package com.jbrunton.pockettimeline.entities.data;

import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.List;

import rx.Observable;

@FunctionalInterface
public interface SearchableRepository<T extends Resource> {
    Observable<List<T>> search(String query);
}
