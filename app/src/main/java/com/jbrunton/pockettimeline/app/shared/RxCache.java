package com.jbrunton.pockettimeline.app.shared;

import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public class RxCache {
    private Map<String, Observable> cache = new HashMap<>();

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
        String compoundKey = keyFor(context, key);
        return fetch(compoundKey);
    }

    public void invalidate(String key) {
        cache.remove(key);
    }

    public void invalidate(Context context) {
        String scope = scopeFor(context);
        Iterator<String> keyIterator = cache.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (key.startsWith(scope)) {
                keyIterator.remove();
            }
        }
    }

    protected String keyFor(Context context, String key) {
        return scopeFor(context) + key;
    }

    private String scopeFor(Context context) {
        return context.getClass().getName() + "/";
    }
}
