package com.jbrunton.pockettimeline.app.shared;

import android.content.Context;

import com.jbrunton.pockettimeline.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.functions.Func0;
import rx.observers.TestSubscriber;
import rx.subjects.ReplaySubject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RxCacheTest {
    private ReplaySubject<Integer> counter;

    private final RxCache cache = new RxCache();

    @Before public void setUp() {
        counter = ReplaySubject.create();
    }

    @Test public void shouldCacheObservables() {
        cache.cache("key", counter);
        Observable<Integer> cachedCounter = cache.fetch("key");

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        cachedCounter.subscribe(testSubscriber);

        counter.onNext(1);
        counter.onNext(2);
        counter.onNext(3);

        testSubscriber.assertValues(1, 2, 3);
    }

    @Test public void cachedObservablesShouldReplayLastValue() {
        cache.cache("key", counter);
        Observable<Integer> cachedCounter = cache.fetch("key");

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        counter.onNext(1);

        cachedCounter.subscribe(testSubscriber);
        testSubscriber.assertValues(1);
    }

    @Test public void shouldInvalidateCacheByKey() {
        cache.cache("key", counter);
        assertThat(cache.fetch("key")).isNotNull();

        cache.invalidate("key");

        assertThat(cache.fetch("key")).isNull();
    }

    @Test public void shouldCacheObservablesByContext() {
        Context context = mock(Context.class);

        cache.cache(context, "key", counter);
        Observable<Integer> cachedCounter = cache.fetch(context, "key");

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        counter.onNext(1);

        cachedCounter.subscribe(testSubscriber);
        testSubscriber.assertValues(1);
    }

    @Test public void shouldInvalidateCacheByContext() {
        Context context1 = RuntimeEnvironment.application,
                context2 = mock(Context.class);

        cache.cache(context1, "key", counter);
        cache.cache(context2, "key", counter);

        cache.invalidate(context1);

        assertThat(cache.fetch(context1, "key")).isNull();
        assertThat(cache.fetch(context2, "key")).isNotNull();
    }

    @Test public void shouldCreateObservableWithFactoryIfMissing() {
        Func0<Observable<Integer>> factory = factoryThatReturns(counter);
        Observable<Integer> cachedCounter = cache.cache("key", factory);

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        cachedCounter.subscribe(testSubscriber);

        counter.onNext(1);

        testSubscriber.assertValues(1);
    }

    @Test public void shouldReturnSameInstanceIfCached() {
        Func0<Observable<Integer>> factory = factoryThatReturns(counter);
        Observable<Integer> cachedCounter = cache.cache("key", factory);
        assertThat(cache.cache("key", factory)).isSameAs(cachedCounter);
    }

    @Test public void shouldGenerateCompoundKey() {
        Context context = RuntimeEnvironment.application;
        String compoundKey = cache.keyFor(context, "key");
        assertThat(compoundKey).isEqualTo("com.jbrunton.pockettimeline.TestPocketTimelineApplication/key");
    }

    private Func0<Observable<Integer>> factoryThatReturns(Observable<Integer> observable) {
        return new Func0<Observable<Integer>>() {
            @Override public Observable<Integer> call() {
                return observable;
            }
        };
    }
}
