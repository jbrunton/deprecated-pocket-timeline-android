package com.jbrunton.pockettimeline.entities.models;

import com.google.auto.value.AutoValue;
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

    public Timeline withEvents(List<Event> events) {
        return toBuilder().events(events).build();
    }

    @AutoValue.Builder
    public static abstract class Builder extends AbstractBuilder<Timeline, Timeline.Builder> {
        public abstract Builder title(String title);
        public abstract Builder description(String description);
        public abstract List<Event> getEvents();
        public abstract Builder events(List<Event> events);

        @Override protected void preprocess() {
            List<Event> events = getEvents();
            events(events == null ? null : Collections.unmodifiableList(events));
        }

        @Override public void validate(Timeline instance) {
            super.validate(instance);
            Preconditions.checkState(instance.getTitle().length() > 0, "title must not be empty");
        }
    }
}
