package com.jbrunton.pockettimeline.entities.models;

import org.joda.time.LocalDate;

import java.io.Serializable;

public class Event extends Resource implements Serializable {
    private final LocalDate date;
    private final String title;
    private final String description;

    public static class Builder extends AbstractBuilder<Event, Event.Builder> {
        private LocalDate date;
        private String title;
        private String description;

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        @Override public Event build() {
            return new Event(this);
        }
    }

    protected Event(Builder builder) {
        super(builder);
        this.date = builder.date;
        this.title = builder.title;
        this.description = builder.description;

        validate();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override protected void validate() {
        super.validate();

        if (this.date == null) {
            throw new IllegalStateException("date is null");
        }
        if (this.title == null || this.title.isEmpty()) {
            throw new IllegalStateException("title is empty");
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
