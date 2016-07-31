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
        builder = Event.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .date(DATE);
    }

    @Test public void shouldBuildEvent() throws InvalidInstantiationException {
        Event event = builder.build();

        assertThat(event.getId()).isEqualTo(ID);
        assertThat(event.getTitle()).isEqualTo(TITLE);
        assertThat(event.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(event.getDate()).isEqualTo(DATE);
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
    public void shouldValidateDate() throws InvalidInstantiationException {
        builder.date(null);
        builder.build();
    }

    @Test
    public void shouldNormalizeDescription() throws InvalidInstantiationException {
        builder.description(null);
        assertThat(builder.build().getDescription()).isEqualTo("");
    }
}