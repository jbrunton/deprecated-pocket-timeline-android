package com.jbrunton.pockettimeline.entities.models;

import com.google.common.base.Preconditions;

public abstract class Resource {
    private static final String NEW_RESOURCE_ID = "0";

    public abstract String getId();

    public boolean isNewResource() {
        return NEW_RESOURCE_ID.equals(getId());
    }

    public abstract static class AbstractBuilder<T extends Resource, B extends AbstractBuilder<T, B>> {
        public abstract B id(String id);

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

        public void validate(T instance) throws InvalidInstantiationException {
            Preconditions.checkState(instance.getId().length() > 0, "id must not be empty");
        }

        protected void normalizeValues() {
            // nothing by default
        }

        public B asNewResource() {
            id(NEW_RESOURCE_ID);
            return (B) this;
        }
    }
}
