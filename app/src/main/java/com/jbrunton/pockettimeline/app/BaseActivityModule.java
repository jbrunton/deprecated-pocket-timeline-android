package com.jbrunton.pockettimeline.app;

import android.app.Activity;

import com.jbrunton.pockettimeline.PerActivity;

import dagger.Module;
import dagger.Provides;

public class BaseActivityModule<T extends Activity> {
    protected final T activity;

    public BaseActivityModule(T activity) {
        this.activity = activity;
    }

//    @Provides @PerActivity T provideActivity() {
//        return activity;
//    }
}
