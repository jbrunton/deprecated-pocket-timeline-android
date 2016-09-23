package com.jbrunton.pockettimeline.entities.models;

public interface Instantiable<T> {
    T instantiate() throws InvalidInstantiationException;
}
