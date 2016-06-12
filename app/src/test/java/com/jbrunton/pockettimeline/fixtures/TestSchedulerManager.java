package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.app.shared.SchedulerManager;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class TestSchedulerManager extends SchedulerManager {
    @Override protected Scheduler mainThread() {
        return Schedulers.immediate();
    }

    @Override protected Scheduler backgroundThread() {
        return Schedulers.immediate();
    }
}
