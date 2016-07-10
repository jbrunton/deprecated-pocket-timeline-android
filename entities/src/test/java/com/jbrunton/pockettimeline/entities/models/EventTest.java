package com.jbrunton.pockettimeline.entities.models;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {
    private static final String ID = "1234";
    private static final String TITLE = "Some Title";
    private static final String DESCRIPTION = "Some Desciption";
    private static final LocalDate DATE = new LocalDate(2016, 3, 15);

    private Event.Builder builder;

    @Before public void setUp() {
        builder = new Event.Builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .date(DATE);
    }

    @Test public void shouldBuildEvent() {
        Event event = builder.build();

        assertThat(event.getId()).isEqualTo(ID);
        assertThat(event.getTitle()).isEqualTo(TITLE);
        assertThat(event.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(event.getDate()).isEqualTo(DATE);
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
    public void shouldValidateDate() {
        builder.date(null);
        builder.build();
    }
}