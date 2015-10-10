package com.jbrunton.pockettimeline.app.shared;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.jbrunton.pockettimeline.BuildConfig;

import org.assertj.core.api.AbstractAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;
import rx.observers.TestSubscriber;
import rx.subjects.ReplaySubject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RxCacheTest {
    private ReplaySubject<Integer> counter;
    private Fragment owner;

    private final RxCache cache = new RxCache();
    private Func0<Observable<Integer>> factory;
    private Fragment otherOwner;

    @Before public void setUp() {
        counter = ReplaySubject.create();
        owner = new TestFragment();
        factory = factoryThatReturns(counter);
        otherOwner = mock(Fragment.class);
    }

    @Test public void shouldReturnObservable() {
        // act
        Observable<Integer> cachedCounter = cache.cache(owner, "1", "key", factory);

        // assert
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        cachedCounter.subscribe(testSubscriber);

        counter.onNext(1);
        counter.onNext(2);
        counter.onNext(3);

        testSubscriber.assertValues(1, 2, 3);
    }

    @Test public void shouldCacheObservables() {
        factory = spy(factory);
        Observable<Integer> firstResult = cache.cache(owner, "1", "key", factory);

        Observable<Integer> secondResult = cache.cache(owner, "1", "key", factory);

        assertThat(firstResult).isSameAs(secondResult);
        verify(factory, times(1)).call();
    }

    @Test public void cachedObservablesShouldReplayLastValue() {
        Observable<Integer> cachedCounter = cache.cache(owner, "1", "key", factory);

        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        counter.onNext(1);

        cachedCounter.subscribe(testSubscriber);
        testSubscriber.assertValues(1);
    }

    @Test public void shouldFetchObservables() {
        // arrange
        cache.cache(owner, "1", "key", factory);

        // act
        Observable<Integer> cachedCounter = cache.fetch(owner, "1", "key");

        // assert
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        cachedCounter.subscribe(testSubscriber);

        counter.onNext(1);

        testSubscriber.assertValues(1);
    }

    @Test public void shouldInvalidateCacheByContext() {
        cache.cache(owner, "1", "key", factory);
        cache.cache(otherOwner, "1", "key", factory);

        cache.invalidate(owner, "1");

        assertThat(cache.fetch(owner, "1", "key")).isNull();
        assertThat(cache.fetch(otherOwner, "1", "key")).isNotNull();
    }

    @Test public void shouldInvalidateCacheByContextId() {
        cache.cache(owner, "1", "key", factory);
        cache.cache(owner, "2", "key", factory);

        cache.invalidate(owner, "1");

        assertThat(cache.fetch(owner, "1", "key")).isNull();
        assertThat(cache.fetch(owner, "2", "key")).isNotNull();
    }

    @Test public void shouldReturnCompoundKey() {
        String compoundKey = cache.keyFor(owner, null, "key");
        assertThat(compoundKey).isEqualTo("com.jbrunton.pockettimeline.app.shared.RxCacheTest$TestFragment/key");
    }

    @Test public void shouldReturnCompoundKeyWithInstanceId() {
        String compoundKey = cache.keyFor(owner, "instanceId", "key");
        assertThat(compoundKey).isEqualTo("com.jbrunton.pockettimeline.app.shared.RxCacheTest$TestFragment:instanceId/key");
    }

    private Func0<Observable<Integer>> factoryThatReturns(Observable<Integer> observable) {
        return new Func0<Observable<Integer>>() {
            @Override public Observable<Integer> call() {
                return observable;
            }
        };
    }

    public static class TestFragment extends Fragment {}
}
