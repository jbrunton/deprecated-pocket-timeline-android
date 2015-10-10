package com.jbrunton.pockettimeline.app.shared;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func0;

@Singleton
public class RxCache {
    private Map<String, Observable> cache = new HashMap<>();

    @Inject public RxCache() {}

    public <T> Observable<T> cache(Object owner, String ownerId, String key, Func0<Observable<T>> factory) {
        String compoundKey = keyFor(owner, ownerId, key);
        Observable<T> observable = cache.get(compoundKey);
        if (observable == null) {
            cache.put(compoundKey, factory.call().cache(1));
            observable = cache.get(compoundKey);
        }
        return observable;
    }

    public void invalidate(Object owner, String ownerId) {
        String scope = scopeFor(owner, ownerId);
        Iterator<String> keyIterator = cache.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (key.startsWith(scope)) {
                keyIterator.remove();
            }
        }
    }

    public <T> Observable<T> fetch(Object owner, String ownerId, String key) {
        return cache.get(keyFor(owner, ownerId, key));
    }

    protected String keyFor(Object owner, String ownerId, String key) {
        return scopeFor(owner, ownerId) + key;
    }

    private String scopeFor(Object owner, String ownerId) {
        String scope = owner.getClass().getName();
        if (ownerId != null) {
            scope = scope + ":" + ownerId;
        }
        return scope + "/";
    }
}
