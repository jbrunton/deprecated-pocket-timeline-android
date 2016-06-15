package com.jbrunton.pockettimeline.app.shared;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulerManager {
    protected Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    protected Scheduler backgroundThread() {
        return Schedulers.io();
    }

    public <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(backgroundThread())
                .observeOn(mainThread());
    }
}
