package com.jbrunton.pockettimeline.fixtures;


import android.support.v4.app.Fragment;

import com.jbrunton.pockettimeline.BuildConfig;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

@Config(constants = BuildConfig.class, sdk = 21)
public class FragmentTestSuite<T extends Fragment> {
    private T fragment;
    private BaseActivity activity;
    private ActivityController<BaseActivity> controller;

    protected void configure(T fragment) {
        this.fragment = fragment;

        controller = Robolectric.buildActivity(BaseActivity.class);
        activity = controller.create().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, null)
                .commit();
    }

    protected T fragment() {
        return fragment;
    }

    protected BaseActivity activity() {
        return activity;
    }

    protected ActivityController<BaseActivity> controller() {
        return controller;
    }
}
