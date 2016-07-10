package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.entities.models.Event;

import org.joda.time.LocalDate;

public class EventFactory {
    private static int seed = 0;

    private EventFactory() {
        // utility class
    }

    public static Event.Builder builder() {
        ++seed;
        return new Event.Builder()
                .id(Integer.toString(seed))
                .title("Event " + seed)
                .description("Event " + seed + " Description")
                .date(new LocalDate(1950 + seed, 1, 1));
    }

    public static Event create() {
        return builder().build();
    }
}
