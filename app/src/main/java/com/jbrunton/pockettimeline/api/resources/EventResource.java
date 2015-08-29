package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

public class EventResource extends Resource {
    protected String title;
    protected String description;

    public static Event toModel(EventResource resource) {
        return new Event(resource.id, resource.title, resource.description);
    }

}
