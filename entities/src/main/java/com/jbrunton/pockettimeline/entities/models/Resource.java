package com.jbrunton.pockettimeline.entities.models;

import com.jbrunton.pockettimeline.entities.data.Builder;

public class Resource {
    private final String id;

    private static final String NEW_RESOURCE_ID = "0";

    protected Resource(AbstractBuilder builder) {
        this.id = builder.id;
    }

    public Resource(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isNewResource() {
        return NEW_RESOURCE_ID.equals(id);
    }

    protected void validate() {
        if (this.id == null || this.id == "") {
            throw new IllegalStateException("id is null");
        }
    }

    public abstract static class AbstractBuilder<T extends Resource, B extends AbstractBuilder> implements Builder<T> {
        private String id;

        public B id(String id) {
            this.id = id;
            return (B) this;
        }

        public B asNewResource() {
            this.id = NEW_RESOURCE_ID;
            return (B) this;
        }
    }
}
