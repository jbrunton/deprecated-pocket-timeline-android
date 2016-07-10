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
        builder = new Timeline.Builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .events(EVENTS);
    }

    @Test public void shouldBuildTimeline() {
        Timeline timeline = builder.build();

        assertThat(timeline.getId()).isEqualTo(ID);
        assertThat(timeline.getTitle()).isEqualTo(TITLE);
        assertThat(timeline.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(timeline.getEvents()).containsExactlyElementsOf(EVENTS);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldHaveImmutableEventsList() {
        List<Event> mutableList = new LinkedList<>();
        mutableList.addAll(EVENTS);
        builder.events(mutableList);

        Timeline timeline = builder.build();

        timeline.getEvents().clear();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldValidateNullTitle() {
        builder.title(null);
        builder.build();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldValidateEmptyTitle() {
        builder.title("");
        builder.build();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldValidateEvents() {
        builder.events(null);
        builder.build();
    }
}