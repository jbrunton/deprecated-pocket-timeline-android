package com.jbrunton.pockettimeline.api.service;

import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.api.resources.TimelineResource;
import com.jbrunton.pockettimeline.models.Event;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface RestService {
    @GET("/timelines.json")
    Observable<List<TimelineResource>> getTimelines();

    @GET("/timelines/{id}.json")
    Observable<TimelineResource> getTimeline(@Path("id") String timelineId);

    @GET("/events.json")
    Observable<List<EventResource>> getEvents();

    @GET("/timelines/{id}/events.json")
    Observable<List<EventResource>> getEvents(@Path("id") String timelineId);
}