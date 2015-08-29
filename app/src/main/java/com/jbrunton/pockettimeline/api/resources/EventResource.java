package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

import org.joda.time.LocalDate;

public class EventResource extends Resource {
    protected LocalDate date;
    protected String title;
    protected String description;

    public static Event toModel(EventResource resource) {
        return new Event(resource.id, resource.date, resource.title, resource.description);
    }

}
