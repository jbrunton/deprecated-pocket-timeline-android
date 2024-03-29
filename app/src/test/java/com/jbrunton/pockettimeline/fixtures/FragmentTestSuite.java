package com.jbrunton.pockettimeline.fixtures;


import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import com.jbrunton.pockettimeline.BuildConfig;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

@Config(constants = BuildConfig.class, sdk = 21)
public abstract class FragmentTestSuite<T extends Fragment> {
    private T fragment;
    private TestActivity activity;
    private ActivityController<TestActivity> controller;

    protected T fragment() {
        return fragment;
    }

    protected BaseActivity activity() {
        return activity;
    }

    protected ActivityController<TestActivity> controller() {
        return controller;
    }

    protected PocketTimelineApplication application() {
        return (PocketTimelineApplication) RuntimeEnvironment.application;
    }

    protected String getString(int resId) {
        return application().getString(resId);
    }

    protected String getString(int resId, Object... formatArgs) {
        return application().getString(resId, formatArgs);
    }

    protected TextView textView(int resId) {
        return (TextView) fragment().getView().findViewById(resId);
    }

    protected Button button(int resId) {
        return (Button) textView(resId);
    }

    protected void configureTestSuite(T fragment) {
        this.fragment = fragment;

        controller = Robolectric.buildActivity(TestActivity.class);
        activity = controller.create().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, null)
                .commit();
    }

    public static class TestActivity extends BaseActivity {
        @Override protected void setupActivityComponent() {
        }
    }
}
