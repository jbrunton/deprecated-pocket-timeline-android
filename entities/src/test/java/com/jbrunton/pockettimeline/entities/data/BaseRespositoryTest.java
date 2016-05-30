package com.jbrunton.pockettimeline.entities.data;

import com.google.common.base.Optional;
import com.jbrunton.pockettimeline.entities.models.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
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
        Observable<Optional<Resource>> resourceOne = repository.find("1");
        assertThat(latest(resourceOne).get()).isSameAs(RESOURCE_ONE);
    }

    @Test public void shouldReturnNoneIfResourceDoesNotExist() {
        Observable<Optional<Resource>> resourceThree = repository.find("3");
        assertThat(latest(resourceThree).isPresent()).isFalse();
    }

    private Repository<Resource> createTestRepository() {
        return new BaseRepository<Resource>() {
            @Override public Observable<List<Resource>> all() {
                return just(RESOURCES);
            }
        };
    }

    private static <T> T latest(Observable<T> observable) {
        TestSubscriber<T> testSubscriber = TestSubscriber.create();
        observable.subscribe(testSubscriber);
        List<T> events = testSubscriber.getOnNextEvents();
        return events.get(events.size() - 1);
    }
}
