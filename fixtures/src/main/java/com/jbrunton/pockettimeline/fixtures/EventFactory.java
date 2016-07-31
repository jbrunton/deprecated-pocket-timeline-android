package com.jbrunton.pockettimeline.fixtures;

import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.entities.models.InvalidInstantiationException;

import org.joda.time.LocalDate;

public class EventFactory {
    private static int seed = 0;

    private EventFactory() {
        // utility class
    }

    public static Event.Builder builder() {
        ++seed;
        return Event.builder()
                .id(Integer.toString(seed))
                .title("Event " + seed)
                .description("Event " + seed + " Description")
                .date(new LocalDate(1950 + seed, 1, 1));
    }

    public static Event create() {
        try {
            return builder().build();
        } catch (InvalidInstantiationException e) {
            throw new IllegalStateException(e);
        }
    }
}
