package com.jbrunton.pockettimeline.app.shared;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;

public class RxCacheActivity extends AppCompatActivity {
    @Inject RxCache cache;

    @Override public void onDestroy() {
        super.onDestroy();

        if (isFinishing()) {
            cache.invalidate(this, ownerId());
        }
    }

    /**
     * Use this to distinguish between instances of the fragment if you may have multiple instances
     * active at the same time.
     *
     * @return A unique identifier for the fragment.
     */
    protected String ownerId() {
        return null;
    }

    protected <T> Observable<T> cache(String key, Func0<Observable<T>> factory) {
        return cache.cache(this, ownerId(), key, factory);
    }

    protected <T> Observable<T> fetch(String key) {
        return cache.fetch(this, ownerId(), key);
    }

    protected void invalidate(String key) {
        cache.invalidate(this, ownerId(), key);
    }
}
