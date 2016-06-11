package com.jbrunton.pockettimeline;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.VisibleForTesting;

import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.ApplicationModule;
import com.jbrunton.pockettimeline.app.DaggerApplicationComponent;

public class PocketTimelineApplication extends Application {
    private ApplicationComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .restServiceModule(new RestServiceModule(getString(R.string.base_url)))
                .build();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public ApplicationComponent component() {
        return component;
    }

    @VisibleForTesting public void setComponent(ApplicationComponent component) {
        this.component = component;
    }
}
