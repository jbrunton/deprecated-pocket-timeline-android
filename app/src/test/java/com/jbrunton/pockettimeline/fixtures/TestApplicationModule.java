package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.app.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.spy;

@Module
public class TestApplicationModule {
    @Provides @Singleton Navigator provideNavigator() {
        return spy(Navigator.class);
    }
}
