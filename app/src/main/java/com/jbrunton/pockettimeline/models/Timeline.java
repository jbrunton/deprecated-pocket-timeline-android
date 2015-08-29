package com.jbrunton.pockettimeline.models;

public class Timeline extends Resource {
    private final String title;
    private final String description;

    public Timeline(String id, String title, String description) {
        super(id);
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
