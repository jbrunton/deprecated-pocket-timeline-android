package com.jbrunton.pockettimeline.api;

import dagger.Component;

@Component(modules = RestServiceModule.class)
public interface RestServiceComponent {
    RestService createService();
}
