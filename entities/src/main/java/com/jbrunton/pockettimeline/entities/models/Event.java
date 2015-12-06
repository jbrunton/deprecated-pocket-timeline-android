package com.jbrunton.pockettimeline.entities.models;

import org.joda.time.LocalDate;

import java.io.Serializable;

public class Event extends Resource implements Serializable {
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
