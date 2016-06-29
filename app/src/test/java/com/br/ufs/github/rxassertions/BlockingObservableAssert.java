package com.br.ufs.github.rxassertions;

import org.assertj.core.api.AbstractAssert;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import rx.Notification;
import rx.observables.BlockingObservable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ubiratansoares on 5/11/16.
 */

public class BlockingObservableAssert<T>
        extends AbstractAssert<BlockingObservableAssert<T>, BlockingObservable<T>> {

    private List<Throwable> onErrorEvents;
    private List<T> onNextEvents;
    private List<Notification<T>> onCompletedEvents;

    public BlockingObservableAssert(BlockingObservable<T> actual) {
        super(actual, BlockingObservableAssert.class);
        TestSubscriber<T> subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);
        onErrorEvents = subscriber.getOnErrorEvents();
        onNextEvents = subscriber.getOnNextEvents();
        onCompletedEvents = subscriber.getOnCompletedEvents();
    }

    public BlockingObservableAssert<T> completes() {
        assertThat(onCompletedEvents).isNotNull().isNotEmpty();
        return this;
    }

    public BlockingObservableAssert<T> notCompletes() {
        assertThat(onCompletedEvents).isNullOrEmpty();
        return this;
    }

    public BlockingObservableAssert<T> emissionsCount(int count) {
        assertThat(onNextEvents).isNotNull().isNotEmpty().hasSize(count);
        return this;
    }

    public BlockingObservableAssert<T> fails() {
        assertThat(onErrorEvents).isNotNull().isNotEmpty();
        return this;
    }

    public BlockingObservableAssert<T> failsWithThrowable(Class thowableClazz) {
        assertThat(onErrorEvents).isNotNull();
        assertThat(onErrorEvents.get(0)).isInstanceOf(thowableClazz);
        return this;
    }

    public BlockingObservableAssert<T> emitsNothing() {
        assertThat(onNextEvents).isEmpty();
        return this;
    }

    public BlockingObservableAssert<T> receivedTerminalEvent() {
        assertThat(onCompletedEvents).isNotNull();
        assertThat(onErrorEvents).isNotNull();
        assertThat(onCompletedEvents.size() + onErrorEvents.size()).isEqualTo(1);
        return this;
    }

    public BlockingObservableAssert<T> withoutErrors() {
        assertThat(onErrorEvents).isNotNull().isEmpty();
        return this;
    }

    public BlockingObservableAssert<T> expectedSingleValue(T expected) {
        assertThat(onNextEvents)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(onNextEvents.get(0)).isEqualTo(expected);
        return this;
    }

    public BlockingObservableAssert<T> expectedValues(T... expected) {
        return expectedValues(Arrays.asList(expected));
    }

    public BlockingObservableAssert<T> expectedValues(Collection<T> ordered) {
        assertThat(onNextEvents)
                .isNotNull()
                .isNotEmpty()
                .hasSameSizeAs(ordered)
                .containsAll(ordered);
        return this;
    }

}
