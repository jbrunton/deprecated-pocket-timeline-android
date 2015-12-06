package com.jbrunton.pockettimeline.app.shared;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulerManager {
    private final Scheduler mainThread;
    private final Scheduler backgroundThread;

    public SchedulerManager(Scheduler mainThread, Scheduler backgroundThread) {
        this.mainThread = mainThread;
        this.backgroundThread = backgroundThread;
    }

    @Inject public SchedulerManager() {
        this(AndroidSchedulers.mainThread(), Schedulers.io());
    }

    public <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(backgroundThread)
                .observeOn(mainThread);
    }
}
