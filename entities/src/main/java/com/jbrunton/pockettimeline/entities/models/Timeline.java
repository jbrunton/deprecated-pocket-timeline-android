package com.jbrunton.pockettimeline.entities.models;

import java.util.Collections;
import java.util.List;

public class Timeline extends Resource {
    private final String title;
    private final String description;
    private final List<Event> events;

    public static class Builder extends AbstractBuilder<Timeline, Timeline.Builder> {
        private String title;
        private String description;
        private List<Event> events;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder events(List<Event> events) {
            this.events = events;
            return this;
        }

        @Override public Timeline build() {
            return new Timeline(this);
        }
    }

    public Timeline(Builder builder) {
        super(builder);
        this.title = builder.title;
        this.description = builder.description;
        this.events = builder.events == null ? null : Collections.unmodifiableList(builder.events);

        validate();
    }

    public Timeline withEvents(List<Event> events) {
        return new Builder()
                .id(getId())
                .title(getTitle())
                .description(getDescription())
                .events(events)
                .build();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override protected void validate() {
        super.validate();

        if (this.title == null || this.title.isEmpty()) {
            throw new IllegalStateException("title is empty");
        }

        if (this.events == null) {
            throw new IllegalStateException("events is null");
        }
    }

    @Override
    public String toString() {
        return String.format("Timeline(%s) [%s]", getTitle(), hashCode());
    }
}
