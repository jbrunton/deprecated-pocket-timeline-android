package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.models.Timeline;

public class TimelineResource {
    private String id;
    private String title;
    private String description;

    public static Timeline toModel(TimelineResource resource) {
        return new Timeline(resource.id, resource.title, resource.description);
    }
}
