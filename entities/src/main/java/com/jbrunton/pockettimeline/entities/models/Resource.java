package com.jbrunton.pockettimeline.entities.models;

import com.google.common.base.Preconditions;

public abstract class Resource {
    private static final String NEW_RESOURCE_ID = "0";

    public abstract String getId();

    public boolean isNewResource() {
        return NEW_RESOURCE_ID.equals(getId());
    }

    public abstract static class AbstractResourceBuilder<T extends Resource, B extends AbstractResourceBuilder<T, B>> extends AbstractBuilder<T, B> {
        public abstract B id(String id);

        @Override protected void validate(T instance) throws InvalidInstantiationException {
            Preconditions.checkState(instance.getId().length() > 0, "id must not be empty");
        }

        public B asNewResource() {
            id(NEW_RESOURCE_ID);
            return (B) this;
        }
    }
}
