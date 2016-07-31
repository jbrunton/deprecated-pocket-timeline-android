package com.jbrunton.pockettimeline.entities.models;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Timeline extends Resource {
    public abstract String getTitle();
    public abstract String getDescription();
    public abstract List<Event> getEvents();

    public static Builder builder() {
        return new AutoValue_Timeline.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder extends AbstractBuilder<Timeline.Builder> {
        public abstract Builder title(String title);
        public abstract Builder description(String description);
        public abstract Builder events(List<Event> events);

        public abstract Timeline build();
    }
}
