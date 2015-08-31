package com.jbrunton.pockettimeline.fixtures;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jbrunton.pockettimeline.BuildConfig;
import com.jbrunton.pockettimeline.Injects;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.quiz.QuizFragmentTest;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

@Config(constants = BuildConfig.class, sdk = 21)
public class FragmentTestSuite<T extends Fragment> {
    private T fragment;
    private BaseActivity activity;
    private ActivityController<BaseActivity> controller;

    private void configureFragment(T fragment) {
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

    protected PocketTimelineApplication application() {
        return (PocketTimelineApplication) RuntimeEnvironment.application;
    }

    private void configureComponent(ApplicationComponent component) {
        application().setComponent(component);
    }

    private <T> void inject(T object) {
        ApplicationComponent component = application().component();
        ((Injects<T>) component).inject(object);
    }

    protected ConfigureDsl configure() {
        return new ConfigureDsl();
    }

    protected class ConfigureDsl {
        public ConfigureDsl fragment(T fragment) {
            configureFragment(fragment);
            return this;
        }

        public ConfigureDsl component(ApplicationComponent component) {
            configureComponent(component);
            return this;
        }

        public ConfigureDsl inject() {
            FragmentTestSuite.this.inject(FragmentTestSuite.this);
            return this;
        }
    }
}
