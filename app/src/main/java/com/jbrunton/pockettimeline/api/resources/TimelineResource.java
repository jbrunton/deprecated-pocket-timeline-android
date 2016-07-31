package com.jbrunton.pockettimeline.api.resources;

import com.jbrunton.pockettimeline.entities.models.Instantiable;
import com.jbrunton.pockettimeline.entities.models.InvalidInstantiationException;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.Collections;

public class TimelineResource extends Resource implements Instantiable<Timeline> {
    protected String title;
    protected String description;

    @Override public Timeline instantiate() throws InvalidInstantiationException {
        return Timeline.builder()
                .id(id)
                .title(title)
                .description(description)
                .events(Collections.emptyList())
                .build();
    }
}
