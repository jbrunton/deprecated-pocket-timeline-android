package com.jbrunton.pockettimeline.entities.models;

public abstract class Resource {
    private static final String NEW_RESOURCE_ID = "0";

    public abstract String getId();

    public boolean isNewResource() {
        return NEW_RESOURCE_ID.equals(getId());
    }

    public abstract static class AbstractBuilder<T extends AbstractBuilder<T>> {
        public abstract T id(String id);

        public T asNewResource() {
            id(NEW_RESOURCE_ID);
            return (T) this;
        }
    }
}
