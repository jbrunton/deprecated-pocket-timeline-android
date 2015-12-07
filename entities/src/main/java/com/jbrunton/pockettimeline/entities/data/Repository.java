package com.jbrunton.pockettimeline.entities.data;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.jbrunton.pockettimeline.entities.models.Resource;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;

public interface Repository<T extends Resource> {
    Observable<List<T>> all();
    Observable<Optional<T>> find(String id);
}
