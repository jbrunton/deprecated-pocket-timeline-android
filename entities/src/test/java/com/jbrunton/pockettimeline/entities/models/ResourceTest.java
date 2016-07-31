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

    @Test public void shouldBuildResource() throws InvalidInstantiationException {
        TestResource resource = builder.build();
        assertThat(resource.getId()).isEqualTo(ID);
    }

    @Test(expected = InvalidInstantiationException.class)
    public void shouldValidateNullId() throws InvalidInstantiationException {
        builder.id(null);
        builder.build();
    }

    @Test(expected = InvalidInstantiationException.class)
    public void shouldValidateEmptyId() throws InvalidInstantiationException {
        builder.id("");
        builder.build();
    }


}