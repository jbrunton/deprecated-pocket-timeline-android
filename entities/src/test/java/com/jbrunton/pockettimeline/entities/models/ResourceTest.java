package com.jbrunton.pockettimeline.entities.models;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceTest {
    private TestResource.Builder builder;
    private static final String ID = "1234";

    @Before public void setUp() {
        builder = new TestResource.Builder()
                .id(ID);
    }

    @Test public void shouldBuildResource() {
        TestResource resource = builder.build();
        assertThat(resource.getId()).isEqualTo(ID);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldValidateNullId() {
        builder.id(null);
        builder.build();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldValidateEmptyId() {
        builder.id("");
        builder.build();
    }

    private static class TestResource extends Resource {
        protected TestResource(AbstractBuilder builder) {
            super(builder);
            validate();
        }

        private static class Builder extends AbstractBuilder<TestResource, TestResource.Builder> {
            @Override public TestResource build() {
                return new TestResource(this);
            }
        }
    }
}