package com.jbrunton.pockettimeline.app.shared;

import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func0;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

@Singleton
public class RxCache {
    private Map<String, Observable> cache = new HashMap<>();

    @Inject public RxCache() {}

    public <T> void cache(String key, Observable<T> observable) {
        Subject<T, T> subject = ReplaySubject.create(1);
        observable.subscribe(subject);

        cache.put(key, subject);
    }

    public <T> void cache(Context context, String key, Observable<T> observable) {
        String compoundKey = keyFor(context, key);
        cache(compoundKey, observable);
    }

    public <T> Observable<T> fetch(String key) {
        return cache.get(key);
    }

    public <T> Observable<T> fetch(Context context, String key) {
        return fetch(context, null, key);
    }

    public <T> Observable<T> fetch(Context context, String contextId, String key) {
        String compoundKey = keyFor(context, contextId, key);
        return fetch(compoundKey);
    }

    public void invalidate(String key) {
        cache.remove(key);
    }

    public void invalidate(Context context, String contextId) {
        String scope = scopeFor(context, contextId);
        Iterator<String> keyIterator = cache.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (key.startsWith(scope)) {
                keyIterator.remove();
            }
        }
    }

    public void invalidate(Context context) {
        invalidate(context, null);
    }

    public <T> Observable<T> cache(String key, Func0<Observable<T>> factory) {
        Observable<T> observable = fetch(key);
        if (observable == null) {
            cache(key, factory.call());
            observable = fetch(key);
        }
        return observable;
    }

    public <T> Observable<T> cache(Context context, String key, Func0<Observable<T>> factory) {
        String compoundKey = keyFor(context, key);
        return cache(compoundKey, factory);
    }

    public <T> Observable<T> cache(Context context, String contextId, String key, Func0<Observable<T>> factory) {
        String compoundKey = keyFor(context, contextId, key);
        return cache(compoundKey, factory);
    }

    protected String keyFor(Context context, String key) {
        return scopeFor(context) + key;
    }

    protected String keyFor(Context context, String contextId, String key) {
        return scopeFor(context, contextId) + key;
    }

    private String scopeFor(Context context) {
        return scopeFor(context, null);
    }

    private String scopeFor(Context context, String contextId) {
        String scope = context.getClass().getName();
        if (contextId != null) {
            scope = scope + ":" + contextId;
        }
        return scope + "/";
    }
}
