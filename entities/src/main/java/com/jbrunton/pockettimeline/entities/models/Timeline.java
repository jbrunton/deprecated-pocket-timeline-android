package com.jbrunton.pockettimeline.entities.models;

import com.google.auto.value.AutoValue;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class Timeline extends Resource {
    public abstract String getTitle();
    public abstract String getDescription();
    public abstract List<Event> getEvents();

    public static Builder builder() {
        return new AutoValue_Timeline.Builder();
    }

    public abstract Builder toBuilder();

    public Timeline withEvents(List<Event> events) throws InvalidInstantiationException {
        return toBuilder().events(events).build();
    }

    @AutoValue.Builder
    public abstract static class Builder extends AbstractResourceBuilder<Timeline, Builder> {
        public abstract Builder title(String title);
        public abstract Builder description(String description);
        public abstract Builder events(List<Event> events);

        abstract Optional<String> getDescription();
        abstract Optional<List<Event>> getEvents();

        @Override protected void normalizeValues() {
            if (!getDescription().isPresent()) {
                description("");
            }
            if (getEvents().isPresent()) {
                events(Collections.unmodifiableList(getEvents().get()));
            }
        }

        @Override protected void validate(Timeline instance) throws InvalidInstantiationException {
            super.validate(instance);
            Preconditions.checkState(instance.getTitle().length() > 0, "title must not be empty");
        }
    }
}
