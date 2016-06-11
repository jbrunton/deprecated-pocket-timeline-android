package com.jbrunton.pockettimeline.entities.data;

import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.List;

import rx.Observable;

public interface Repository<T extends Resource> {
    Observable<List<T>> all();
    Observable<T> find(String id);
}
