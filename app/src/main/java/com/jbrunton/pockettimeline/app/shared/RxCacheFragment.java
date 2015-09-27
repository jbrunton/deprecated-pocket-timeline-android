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
            cache.invalidate(getContext(), contextId());
        }
    }

    protected String contextId() {
        return null;
    }

    protected <T> Observable<T> cache(String key, Func0<Observable<T>> factory) {
        return cache.cache(getContext(), contextId(), key, factory);
    }
}
