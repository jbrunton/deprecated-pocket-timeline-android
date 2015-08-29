package com.jbrunton.pockettimeline.models;

import org.joda.time.LocalDate;

public class Event extends Resource {
    private final LocalDate date;
    private final String title;
    private final String description;

    public Event(String id, LocalDate date, String title, String description) {
        super(id);
        this.date = date;
        this.title = title;
        this.description = description;
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

    @Override
    public String toString() {
        return getTitle();
    }
}
