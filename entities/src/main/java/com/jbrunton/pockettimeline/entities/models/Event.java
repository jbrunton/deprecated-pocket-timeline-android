package com.jbrunton.pockettimeline.entities.models;

import com.google.auto.value.AutoValue;

import org.joda.time.LocalDate;

@AutoValue
public abstract class Event extends Resource {
    public abstract LocalDate getDate();
    public abstract String getTitle();
    public abstract String getDescription();

    public static Builder builder() {
        return new AutoValue_Event.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder extends AbstractBuilder<Event.Builder> {
        public abstract Builder date(LocalDate date);
        public abstract Builder title(String title);
        public abstract Builder description(String description);

        public abstract Event build();
    }
}
