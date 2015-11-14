package com.jbrunton.pockettimeline.app.shared;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestProvidersModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.internal.Shadow;
import org.robolectric.shadows.ShadowLooper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
public class BaseFragmentTest extends FragmentTestSuite<BaseFragmentTest.TestFragment, BaseFragmentTest.TestApplicationComponent> {

    @Before public void setUp() {
        configureTestSuite(new TestFragment());
        controller().start().resume();
    }


    public static class TestFragment extends BaseFragment {

    }


    @Singleton @Component(modules = {TestModule.class, RestServiceModule.class, TestProvidersModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent {
        void inject(TestFragment test);
    }

    @Module
    public static class TestModule {

    }

    @Override protected TestApplicationComponent createComponent() {
        return DaggerBaseFragmentTest_TestApplicationComponent.create();
    }
}
