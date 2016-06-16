package com.jbrunton.pockettimeline.api.repositories;

import com.jbrunton.pockettimeline.entities.data.ReadableRepository;
import com.jbrunton.pockettimeline.entities.data.WritableRepository;
import com.jbrunton.pockettimeline.entities.models.Event;

public interface TimelineEventsRepository extends ReadableRepository<Event>, WritableRepository<Event> {
    String timelineId();
}
