package com.jbrunton.pockettimeline.models;

import java.util.List;

public class Timeline extends Resource {
    private final String title;
    private final String description;
    private final List<Event> events;

    public Timeline(String id, String title, String description) {
        this(id, title, description, null);
    }

    public Timeline(String id, String title, String description, List<Event> events) {
        super(id);
        this.title = title;
        this.description = description;
        this.events = events;
    }

    public Timeline withEvents(List<Event> events) {
        return new Timeline(getId(), getTitle(), getDescription(), events);
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

    @Override
    public String toString() {
        return getTitle();
    }
}
