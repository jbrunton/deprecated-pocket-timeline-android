package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.entities.models.Instantiable;
import com.jbrunton.pockettimeline.entities.models.InvalidInstantiationException;

import org.joda.time.LocalDate;

public class EventResource extends Resource implements Instantiable<Event> {
    protected LocalDate date;
    protected String title;
    protected String description;

    @Override public Event instantiate() throws InvalidInstantiationException {
        return Event.builder()
                .id(id)
                .date(date)
                .title(title)
                .description(description)
                .build();
    }
}
