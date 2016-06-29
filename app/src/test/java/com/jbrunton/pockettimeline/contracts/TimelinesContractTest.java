package com.jbrunton.pockettimeline.contracts;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.api.repositories.http.HttpTimelinesRepository;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;

import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;

import static com.br.ufs.github.rxassertions.RxAssertions.assertThat;

public class TimelinesContractTest extends ConsumerPactTest {

    @Override protected PactFragment createFragment(PactDslWithProvider builder) {
        //Map<String, String> headers = new HashMap<String, String>();
        //headers.put("testreqheader", "testreqheadervalue");

        return builder
                .given("WW2 Timeline") // NOTE: Using provider states are optional, you can leave it out
                .uponReceiving("a request for timelines")
                .path("/timelines.json")
                .method("GET")
                //.headers(headers)
                //.body("{\"test\":true}")
                .willRespondWith()
                .status(200)
                //.headers(headers)
                .body(
                        "[\n" +
                                "{\n" +
                                "id: 1,\n" +
                                "title: \"World War II\",\n" +
                                "description: \"Events of the Second World War\",\n" +
                                "url: \"https://timeline-pocketlearningapps.herokuapp.com/timelines/1.json\"\n" +
                                "},\n" +
                         "]"
                ).toFragment();
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
        assertThat(repository.all())
                .completes()
                .withoutErrors()
                .emissionsCount(1);
    }
}