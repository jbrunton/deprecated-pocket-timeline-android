package com.jbrunton.pockettimeline.api.resources;

import com.google.gson.annotations.SerializedName;

import org.joda.time.LocalDate;

public class EventRequest {
    @SerializedName("date") final LocalDate date;
    @SerializedName("title") final String title;

    public EventRequest(LocalDate date, String title) {
        this.date = date;
        this.title = title;
    }
}
