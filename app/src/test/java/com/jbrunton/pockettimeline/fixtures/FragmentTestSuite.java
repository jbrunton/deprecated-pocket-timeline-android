package com.jbrunton.pockettimeline.fixtures;


import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import com.jbrunton.pockettimeline.BuildConfig;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.mockito.Mockito.mock;

@Config(constants = BuildConfig.class, sdk = 21)
public abstract class FragmentTestSuite<T extends Fragment, C extends ApplicationComponent> {
    private T fragment;
    private BaseActivity activity;
    private ActivityController<BaseActivity> controller;
    private C component;

    protected T fragment() {
        return fragment;
    }

    protected BaseActivity activity() {
        return activity;
    }

    protected ActivityController<BaseActivity> controller() {
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
        configureComponent(createComponent());
        configureFragment(fragment);
    }

    protected C component() {
        return component;
    }

    private void configureComponent(C component) {
        this.component = component;
        application().setComponent(component);
    }

    private void configureFragment(T fragment) {
        this.fragment = fragment;

        controller = Robolectric.buildActivity(BaseActivity.class);
        activity = controller.create().get();

        activity.getSupportFragmentManager().beginTransaction()
                .add(fragment, null)
                .commit();
    }

    protected abstract C createComponent();
}
