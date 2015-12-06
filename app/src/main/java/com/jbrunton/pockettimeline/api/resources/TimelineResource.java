package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.entities.models.Timeline;

public class TimelineResource extends Resource {
    protected String title;
    protected String description;

    public static Timeline toModel(TimelineResource resource) {
        return new Timeline(resource.id, resource.title, resource.description);
    }
}
