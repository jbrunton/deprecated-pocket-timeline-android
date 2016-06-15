package com.jbrunton.pockettimeline.app;

import android.app.Activity;

import com.jbrunton.pockettimeline.PerActivity;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity protected Activity provideActivity() {
        return activity;
    }

    @Provides @PerActivity protected Navigator provideNavigator() {
        return new Navigator(activity);
    }
}
