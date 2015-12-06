package com.jbrunton.pockettimeline.entities.data;


import com.jbrunton.pockettimeline.entities.models.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.instrument.UnmodifiableClassException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class RepositoryTest {
    private Repository<Resource> repository;

    private final Resource RESOURCE_ONE = new Resource("1");
    private final Resource RESOURCE_TWO = new Resource("2");
    private final List<Resource> RESOURCES = asList(RESOURCE_ONE, RESOURCE_TWO);

    @Before public void setUp() {
        repository = new Repository<>();
    }

    @Test public void shouldReturnEmptyListByDefault() {
        assertThat(all()).isEmpty();
    }

    @Test public void shouldSetData() {
        repository.set(RESOURCES);
        assertThat(all()).contains(RESOURCE_ONE, RESOURCE_TWO);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnImmutableCollection() {
        all().add(RESOURCE_ONE);
    }

    @Test public void shouldFindResourceById() {
        repository.set(RESOURCES);
        Resource found = repository.find("1").toBlocking().single();
        assertThat(found).isSameAs(RESOURCE_ONE);
    }

    private List<Resource> all() {
        return repository.all().toBlocking().single();
    }
}