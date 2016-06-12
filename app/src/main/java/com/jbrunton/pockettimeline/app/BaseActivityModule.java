package com.jbrunton.pockettimeline.app;

import android.app.Activity;

public class BaseActivityModule<T extends Activity> {
    protected final T activity;

    public BaseActivityModule(T activity) {
        this.activity = activity;
    }

//    @Provides @PerActivity T provideActivity() {
//        return activity;
//    }
}
