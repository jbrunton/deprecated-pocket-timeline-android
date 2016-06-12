package com.jbrunton.pockettimeline.api;

import com.jbrunton.pockettimeline.entities.data.ReadableRepository;
import com.jbrunton.pockettimeline.entities.data.SearchableRepository;
import com.jbrunton.pockettimeline.entities.data.WritableRepository;
import com.jbrunton.pockettimeline.entities.models.Event;

public interface EventsRepository extends ReadableRepository<Event>, SearchableRepository<Event>
{
}
