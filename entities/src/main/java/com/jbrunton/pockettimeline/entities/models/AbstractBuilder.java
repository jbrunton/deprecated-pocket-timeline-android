package com.jbrunton.pockettimeline.entities.models;

public abstract class AbstractBuilder<T, B extends AbstractBuilder<T, B>> {
    public abstract T autoBuild();

    public T build() throws InvalidInstantiationException {
        try {
            normalizeValues();
            T instance = autoBuild();
            validate(instance);
            return instance;
        } catch (IllegalStateException e) {
            throw new InvalidInstantiationException(e);
        }
    }

    protected void validate(T instance) throws InvalidInstantiationException {
        // nothing by default
    }

    protected void normalizeValues() {
        // nothing by default
    }
}
