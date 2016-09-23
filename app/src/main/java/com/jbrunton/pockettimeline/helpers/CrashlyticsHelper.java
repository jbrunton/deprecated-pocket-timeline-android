package com.jbrunton.pockettimeline.helpers;

import com.crashlytics.android.Crashlytics;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.jbrunton.pockettimeline.BuildConfig;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.entities.models.Instantiable;
import com.jbrunton.pockettimeline.entities.models.InvalidInstantiationException;

import java.util.List;

import io.fabric.sdk.android.Fabric;

public class CrashlyticsHelper {
    private final PocketTimelineApplication application;

    public CrashlyticsHelper(PocketTimelineApplication application) {
        this.application = application;
    }

    public void initialize() {
        if (loggingEnabled()) {
            Fabric.with(application, new Crashlytics());
        }
    }

    public <T> T instantiate(Instantiable<T> instantiable) {
        try {
            return instantiable.instantiate();
        } catch (InvalidInstantiationException e) {
            logException(e);
            throw new IllegalStateException("Sorry, something went wrong.", e);
        }
    }

    public <T> Optional<T> tryInstantiate(Instantiable<T> instantiable) {
        try {
            return Optional.of(instantiable.instantiate());
        } catch (InvalidInstantiationException e) {
            logException(e);
            return Optional.absent();
        }
    }

    public <T> List<T> tryInstantiateAll(List<? extends Instantiable<T>> instantiables) {
        return FluentIterable.from(instantiables)
                .transform(this::tryInstantiate)
                .filter(Optional::isPresent)
                .transform(Optional::get)
                .toList();
    }

    public void logException(Exception e) {
        if (loggingEnabled()) {
            Crashlytics.logException(e);
        }
    }

    private boolean loggingEnabled() {
        return !BuildConfig.DEBUG;
    }
}
