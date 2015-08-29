package com.jbrunton.pockettimeline.app;

import com.jbrunton.pockettimeline.api.service.RestServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RestServiceModule.class)
public interface ApplicationComponent {
    void inject(TimelineFragment fragment);
    void inject(TimelinesFragment fragment);
}
