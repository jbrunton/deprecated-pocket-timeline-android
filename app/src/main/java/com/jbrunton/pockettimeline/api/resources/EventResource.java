package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

public class EventResource {
    private String id;
    private String title;
    private String description;

    public static Event toModel(EventResource resource) {
        return new Event(resource.id, resource.title, resource.description);
    }

}
