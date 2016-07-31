package com.jbrunton.pockettimeline.entities.models;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class Animal {
    abstract String name();
    abstract int numberOfLegs();

    static Builder builder() {
        return new AutoValue_Animal.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder name(String value);
        abstract Builder numberOfLegs(int value);
        abstract Animal build();
    }
}
