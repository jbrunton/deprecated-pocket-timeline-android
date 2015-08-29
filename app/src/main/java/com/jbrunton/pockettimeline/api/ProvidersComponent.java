package com.jbrunton.pockettimeline.api;

import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;

import dagger.Component;

@Component(modules = RestServiceModule.class)
public interface ProvidersComponent {
    TimelinesProvider timelinesProvider();
    EventsProvider eventsProvider();
}
