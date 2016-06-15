package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.ApplicationModule;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;

import org.robolectric.RuntimeEnvironment;

import it.cosenonjaviste.daggermock.DaggerMockRule;

public class TestAppRule extends DaggerMockRule<ApplicationComponent> {

    public TestAppRule() {
        super(ApplicationComponent.class, createApplicationModule(), createRestServiceModule());
        providesMock(RestService.class);
        provides(SchedulerManager.class, new TestSchedulerManager());
        set(application()::setComponent);
    }

    private static ApplicationModule createApplicationModule() {
        return new ApplicationModule(application());
    }

    private static RestServiceModule createRestServiceModule() {
        return new RestServiceModule(application().getString(R.string.base_url));
    }

    private static PocketTimelineApplication application() {
        return (PocketTimelineApplication) RuntimeEnvironment.application;
    }
}
