package com.jbrunton.pockettimeline.entities.models;

import com.jbrunton.pockettimeline.fixtures.EventFactory;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimelineTest {
    private static final String ID = "1234";
    private static final String TITLE = "Some Title";
    private static final String DESCRIPTION = "Some Desciption";
    private static final List<Event> EVENTS = Arrays.asList(
            EventFactory.create(),
            EventFactory.create()
    );

    private Timeline.Builder builder;

    @Before public void setUp() {
        builder = Timeline.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .events(EVENTS);
    }

    @Test public void shouldBuildTimeline() throws InvalidInstantiationException {
        Timeline timeline = builder.build();

        assertThat(timeline.getId()).isEqualTo(ID);
        assertThat(timeline.getTitle()).isEqualTo(TITLE);
        assertThat(timeline.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(timeline.getEvents()).containsExactlyElementsOf(EVENTS);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldHaveImmutableEventsList() throws InvalidInstantiationException {
        List<Event> mutableList = new LinkedList<>();
        mutableList.addAll(EVENTS);
        builder.events(mutableList);

        Timeline timeline = builder.build();

        timeline.getEvents().clear();
    }

    @Test(expected = InvalidInstantiationException.class)
    public void shouldValidateNullTitle() throws InvalidInstantiationException {
        builder.title(null);
        builder.build();
    }

    @Test(expected = InvalidInstantiationException.class)
    public void shouldValidateEmptyTitle() throws InvalidInstantiationException {
        builder.title("");
        builder.build();
    }

    @Test(expected = InvalidInstantiationException.class)
    public void shouldValidateEvents() throws InvalidInstantiationException {
        builder.events(null);
        builder.build();
    }

    @Test
    public void shouldNormalizeDescription() throws InvalidInstantiationException {
        builder.description(null);
        assertThat(builder.build().getDescription()).isEqualTo("");
    }
}