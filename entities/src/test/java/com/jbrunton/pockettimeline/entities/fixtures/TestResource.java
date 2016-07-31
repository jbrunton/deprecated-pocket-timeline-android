package com.jbrunton.pockettimeline.entities.fixtures;

import com.google.auto.value.AutoValue;
import com.jbrunton.pockettimeline.entities.models.Resource;

@AutoValue
public abstract class TestResource extends Resource {
    public static Builder builder() {
        return new AutoValue_TestResource.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder extends AbstractBuilder<TestResource, TestResource.Builder> {

    }
}