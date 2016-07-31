package com.jbrunton.pockettimeline.entities.models;

import com.jbrunton.pockettimeline.entities.fixtures.TestResource;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceTest {
    private TestResource.Builder builder;
    private static final String ID = "1234";

    @Before public void setUp() {
        builder = TestResource.builder()
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


}