package com.jbrunton.pockettimeline.contracts;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.api.repositories.http.HttpTimelinesRepository;
import com.jbrunton.pockettimeline.api.service.RestService;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;

import static org.assertj.core.api.Assertions.assertThat;

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
                        "[{\"id\":1,\"title\":\"World War II\",\"description\":\"Events of the Second World War\",\"url\":\"https://timeline-pocketlearningapps.herokuapp.com/timelines/1.json\"}]"
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

        Timeline expectedTimeline = new Timeline("1", "World War II", "Events of the Second World War");

        List<Timeline> timelines = repository.all().toBlocking().single();
        assertThat(timelines)
                .hasSize(1);
        Timeline timeline = timelines.get(0);
        assertThat(timeline)
                .isEqualToComparingFieldByField(expectedTimeline);
    }
}