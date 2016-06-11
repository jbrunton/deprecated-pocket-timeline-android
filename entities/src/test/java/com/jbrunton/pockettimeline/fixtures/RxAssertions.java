package com.jbrunton.pockettimeline.fixtures;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.observables.BlockingObservable;

public class RxAssertions {

    public static <T> BlockingObservableAssert<T> assertThat(BlockingObservable<T> blocking) {
        return new BlockingObservableAssert<>(blocking);
    }

    public static <T> BlockingObservableAssert<T> assertThat(Observable<T> observable) {
        return new BlockingObservableAssert<>(observable.toBlocking());
    }

    public static <T> BlockingObservableAssert<T> assertThat(Completable completable) {
        return (BlockingObservableAssert<T>) assertThat(completable.toObservable());
    }

    public static <T> BlockingObservableAssert<T> assertThat(Single<T> single) {
        return assertThat(single.toObservable());
    }

    private RxAssertions() {
        throw new AssertionError("No instances, please");
    }
}
