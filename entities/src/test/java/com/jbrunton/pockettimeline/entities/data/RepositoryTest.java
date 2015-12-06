package com.jbrunton.pockettimeline.entities.data;


import com.jbrunton.pockettimeline.entities.models.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.instrument.UnmodifiableClassException;
import java.util.List;

import rx.observers.TestSubscriber;
import rx.subjects.ReplaySubject;

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
        assertThat(all().latest()).isEmpty();
    }

    @Test public void shouldSetData() {
        repository.set(RESOURCES);
        assertThat(all().latest()).contains(RESOURCE_ONE, RESOURCE_TWO);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnImmutableCollection() {
        all().latest().add(RESOURCE_ONE);
    }

    @Test public void shouldFindResourceById() {
        repository.set(RESOURCES);
        Resource found = find("1").latest();
        assertThat(found).isSameAs(RESOURCE_ONE);
    }

    @Test public void shouldNotifySubscribersOfChanges() {
        assertThat(all().latest()).isEmpty();
        repository.set(RESOURCES);
        assertThat(all().latest()).contains(RESOURCE_ONE, RESOURCE_TWO);
    }

    private class AllDsl {
        private final TestSubscriber<List<Resource>> testSubscriber = TestSubscriber.create();

        public AllDsl() {
            repository.all().subscribe(testSubscriber);
        }

        public List<Resource> latest() {
            List<List<Resource>> events = testSubscriber.getOnNextEvents();
            return events.get(events.size() - 1);
        }
    }

    private AllDsl all() {
        return new AllDsl();
    }

    private class FindDsl {
        private final TestSubscriber<Resource> testSubscriber = TestSubscriber.create();

        public FindDsl(String id) {
            repository.find(id).subscribe(testSubscriber);
        }

        public Resource latest() {
            List<Resource> events = testSubscriber.getOnNextEvents();
            return events.get(events.size() - 1);
        }
    }

    private FindDsl find(String id) {
        return new FindDsl(id);
    }


//    private List<Resource> all() {
//        return repository.all().toBlocking().single();
//    }
}