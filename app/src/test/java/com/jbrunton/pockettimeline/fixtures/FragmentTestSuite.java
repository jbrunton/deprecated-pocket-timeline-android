package com.jbrunton.pockettimeline.fixtures;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.jbrunton.pockettimeline.BuildConfig;
import com.jbrunton.pockettimeline.Injects;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.ProvidersModule;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.DaggerApplicationComponent;
import com.jbrunton.pockettimeline.app.quiz.QuizFragmentTest;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import javax.annotation.Resource;

import static org.mockito.Mockito.mock;

@Config(constants = BuildConfig.class, sdk = 21)
public abstract class FragmentTestSuite<T extends Fragment> {
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

    private void configureComponent(ApplicationComponent component) {
        application().setComponent(component);
    }

    private <T> void inject(T object) {
        ApplicationComponent component = application().component();
        ((Injects<T>) component).inject(object);
    }

    protected ConfigureDsl configureTestSuite() {
        configureComponent(createComponent());
        return new ConfigureDsl();
    }

    protected class ConfigureDsl {
        public ConfigureDsl fragment(T fragment) {
            configureFragment(fragment);
            return this;
        }

        public ConfigureDsl inject() {
            FragmentTestSuite.this.inject(FragmentTestSuite.this);
            return this;
        }
    }

    protected abstract ApplicationComponent createComponent();
}
