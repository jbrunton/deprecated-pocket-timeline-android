package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.Collections;

public class TimelineFactory {
    private static int seed = 0;

    private TimelineFactory() {
        // utility class
    }

    public static Timeline.Builder builder() {
        ++seed;
        return new Timeline.Builder()
                .id(Integer.toString(seed))
                .title("Event " + seed)
                .description("Event " + seed + " Description")
                .events(Collections.emptyList());
    }

    public static Timeline create() {
        return builder().build();
    }
}
