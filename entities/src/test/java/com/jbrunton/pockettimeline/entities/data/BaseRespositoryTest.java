package com.jbrunton.pockettimeline.entities.data;

import com.jbrunton.pockettimeline.entities.models.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import rx.Observable;

import static java.util.Arrays.asList;
import static br.ufs.github.rxassertions.RxAssertions.assertThat;
import static rx.Observable.just;

@RunWith(JUnit4.class)
public class BaseRespositoryTest {
    private Repository<Resource> repository;

    private final Resource RESOURCE_ONE = new Resource("1");
    private final Resource RESOURCE_TWO = new Resource("2");
    private final List<Resource> RESOURCES = asList(RESOURCE_ONE, RESOURCE_TWO);

    @Before public void setUp() {
        repository = createTestRepository();
    }

    @Test public void shouldFindRepositoryById() {
        assertThat(repository.find("1"))
                .completes()
                .withoutErrors()
                .expectedSingleValue(RESOURCE_ONE);
    }

    @Test public void shouldErrorIfResourceDoesNotExist() {
        assertThat(repository.find("3"))
                .failsWithThrowable(IllegalStateException.class);
    }

    private Repository<Resource> createTestRepository() {
        return new BaseRepository<Resource>() {
            @Override public Observable<List<Resource>> all() {
                return just(RESOURCES);
            }
        };
    }
}
