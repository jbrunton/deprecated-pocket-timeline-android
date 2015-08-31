package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.helpers.RandomHelper;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DeterministicRandomHelperModule {
    private final Collection<Integer> sequence;

    public DeterministicRandomHelperModule(Integer... sequence) {
        this.sequence = Arrays.asList(sequence);
    }

    @Singleton @Provides RandomHelper randomHelper() {
        return new DeterministicRandomHelper(sequence);
    }
}
