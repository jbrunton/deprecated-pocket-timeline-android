package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.app.shared.SchedulerManager;

import javax.inject.Inject;

import rx.Scheduler;
import rx.schedulers.ImmediateScheduler;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

public class TestSchedulerManager extends SchedulerManager {
    public TestSchedulerManager() {
        super(Schedulers.immediate(), Schedulers.immediate());
    }
}
