package com.jbrunton.pockettimeline;

import android.app.Application;

import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.DaggerApplicationComponent;

public class PocketTimelineApplication extends Application {
    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.create();
    }

    public ApplicationComponent component() {
        return component;
    }
}
