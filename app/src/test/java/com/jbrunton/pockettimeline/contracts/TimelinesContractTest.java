package com.jbrunton.pockettimeline.contracts;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.api.repositories.http.HttpTimelinesRepository;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import rx.Observable;

import static com.br.ufs.github.rxassertions.RxAssertions.assertThat;

public class TimelinesContractTest extends ConsumerPactTest {

    @Override protected PactFragment createFragment(PactDslWithProvider builder) {
        DslPart body = PactDslJsonArray
                .arrayEachLike()
                    .id()
                    .stringType("title")
                    .stringType("description")
                .closeObject();

        return builder
                .given("WW2 Timeline")
                .uponReceiving("a request for timelines")
                    .path("/timelines.json")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body(body)
                .toFragment();
    }

    @Override
    protected String providerName() {
        return "pocket-timeline";
    }

    @Override
    protected String consumerName() {
        return "pocket-timeline-android";
    }

    @Override
    protected void runTest(String url) {
        RestService service = new RestServiceModule(url).provideRestService(null);
        TimelinesRepository repository = new HttpTimelinesRepository(service);

        Observable<List<Timeline>> timelines = repository.all();

        assertThat(timelines)
                .completes()
                .withoutErrors()
                .emissionsCount(1);
    }
}