package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.entities.models.Event;

import org.joda.time.LocalDate;

public class EventResource extends Resource {
    protected LocalDate date;
    protected String title;
    protected String description;

    public static Event toModel(EventResource resource) {
        return new Event.Builder()
                .id(resource.id)
                .date(resource.date)
                .title(resource.title)
                .description(resource.description)
                .build();
    }

}
