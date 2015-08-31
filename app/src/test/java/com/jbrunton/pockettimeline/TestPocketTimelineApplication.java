package com.jbrunton.pockettimeline;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class TestPocketTimelineApplication extends PocketTimelineApplication {
    @Override public Scheduler defaultScheduler() {
        return Schedulers.immediate();
    }
}
