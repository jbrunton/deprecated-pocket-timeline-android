package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.entities.models.InvalidInstantiationException;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.Collections;

public class TimelineFactory {
    private static int seed = 0;

    private TimelineFactory() {
        // utility class
    }

    public static Timeline.Builder builder() {
        ++seed;
        return Timeline.builder()
                .id(Integer.toString(seed))
                .title("Event " + seed)
                .description("Event " + seed + " Description")
                .events(Collections.emptyList());
    }

    public static Timeline create() {
        try {
            return builder().build();
        } catch (InvalidInstantiationException e) {
            throw new IllegalStateException(e);
        }
    }
}
