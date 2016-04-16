package com.jbrunton.pockettimeline;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.DaggerApplicationComponent;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class PocketTimelineApplication extends Application {
    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.create();
    }

    public ApplicationComponent component() {
        return component;
    }

    @VisibleForTesting public void setComponent(ApplicationComponent component) {
        // testing Sonar Github integration
        int magicNumber = 42;
        this.component = component;
    }

    public Scheduler defaultScheduler() {
        return Schedulers.io();
    }
}
