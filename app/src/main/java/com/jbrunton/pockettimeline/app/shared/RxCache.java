package com.jbrunton.pockettimeline.app.shared;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Iterator;
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

    public <T> Observable<T> cache(Context context, String contextId, String key, Func0<Observable<T>> factory) {
        String compoundKey = keyFor(context, contextId, key);
        Observable<T> observable = fetch(compoundKey);
        if (observable == null) {
            cache.put(compoundKey, makeReplayable(factory.call()));
            observable = fetch(compoundKey);
        }
        return observable;
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

    protected String keyFor(Context context, String contextId, String key) {
        return scopeFor(context, contextId) + key;
    }

    protected <T> Observable<T> fetch(String key) {
        return cache.get(key);
    }

    private String scopeFor(Context context, String contextId) {
        String scope = context.getClass().getName();
        if (contextId != null) {
            scope = scope + ":" + contextId;
        }
        return scope + "/";
    }

    private <T> Observable<T> makeReplayable(Observable<T> observable) {
        Subject<T, T> subject = ReplaySubject.create(1);
        observable.subscribe(subject);
        return subject;
    }
}
