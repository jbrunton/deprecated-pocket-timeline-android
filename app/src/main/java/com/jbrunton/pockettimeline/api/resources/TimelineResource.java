package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.Collections;

public class TimelineResource extends Resource {
    protected String title;
    protected String description;

    public static Timeline toModel(TimelineResource resource) {
        return Timeline.builder()
                .id(resource.id)
                .title(resource.title)
                .description(resource.description)
                .events(Collections.emptyList())
                .build();
    }
}
