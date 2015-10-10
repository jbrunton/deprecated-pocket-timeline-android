package com.jbrunton.pockettimeline.app.shared;

import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;

public class RxCacheFragment extends RxFragment {
    @Inject RxCache cache;

    @Override public void onDestroy() {
        super.onDestroy();

        if (getActivity().isFinishing()) {
            cache.invalidate(getContext(), ownerId());
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
        return cache.cache(getContext(), ownerId(), key, factory);
    }

    protected <T> Observable<T> fetch(String key) {
        return cache.fetch(getContext(), ownerId(), key);
    }
}
