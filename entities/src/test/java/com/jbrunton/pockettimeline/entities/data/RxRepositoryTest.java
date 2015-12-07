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

@RunWith(JUnit4.class)
public class RxRepositoryTest {
    private RxRepository<Resource> repository;

    private final Resource RESOURCE_ONE = new Resource("1");
    private final Resource RESOURCE_TWO = new Resource("2");
    private final List<Resource> RESOURCES = asList(RESOURCE_ONE, RESOURCE_TWO);

    @Before public void setUp() {
        repository = new RxRepository<>();
    }

    @Test public void shouldReturnEmptyListByDefault() {
        Observable<List<Resource>> all = repository.all();
        assertThat(latest(all)).isEmpty();
    }

    @Test public void shouldSetData() {
        repository.set(RESOURCES);
        Observable<List<Resource>> all = repository.all();
        assertThat(latest(all)).containsAll(RESOURCES);
    }

    @Test public void shouldSetDataWithObservable() {
        repository.set(Observable.just(RESOURCES));
        Observable<List<Resource>> all = repository.all();
        assertThat(latest(all)).containsAll(RESOURCES);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnImmutableCollection() {
        Observable<List<Resource>> all = repository.all();
        latest(all).add(RESOURCE_ONE);
    }

    @Test public void shouldFindResourceById() {
        repository.set(RESOURCES);
        Observable<Optional<Resource>> resourceOne = repository.find("1");
        assertThat(latest(resourceOne).get()).isSameAs(RESOURCE_ONE);
    }

    @Test public void shouldNotifySubscribersOfChanges() {
        Observable<List<Resource>> all = repository.all();
        Observable<Optional<Resource>> resourceOne = repository.find("1");
        assertThat(latest(all)).isEmpty();
        assertThat(latest(resourceOne).isPresent()).isFalse();

        repository.set(RESOURCES);

        assertThat(latest(all)).containsAll(RESOURCES);
        assertThat(latest(resourceOne).get()).isSameAs(RESOURCE_ONE);
    }

    @Test public void shouldInitWithDefaultValues() {
        RxRepository<Resource> repository = new RxRepository<Resource>() {
            @Override protected Observable<List<Resource>> defaultValues() {
                return Observable.just(RESOURCES);
            }
        };
        Observable<List<Resource>> all = repository.all();
        assertThat(latest(all)).containsAll(RESOURCES);
    }

    private <T> T latest(Observable<T> observable) {
        TestSubscriber<T> testSubscriber = TestSubscriber.create();
        observable.subscribe(testSubscriber);
        List<T> events = testSubscriber.getOnNextEvents();
        return events.get(events.size() - 1);
    }
}