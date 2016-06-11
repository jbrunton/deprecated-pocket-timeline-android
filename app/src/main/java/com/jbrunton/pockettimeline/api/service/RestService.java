package com.jbrunton.pockettimeline.api.service;

import com.jbrunton.pockettimeline.api.resources.EventRequest;
import com.jbrunton.pockettimeline.api.resources.EventResource;
import com.jbrunton.pockettimeline.api.resources.TimelineResource;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestService {
    @GET("/timelines.json")
    Observable<List<TimelineResource>> getTimelines();

    @GET("/timelines/{id}.json")
    Observable<TimelineResource> getTimeline(@Path("id") String timelineId);

    @GET("/events.json")
    Observable<List<EventResource>> getEvents();

    @GET("/events/search.json")
    Observable<List<EventResource>> searchEvents(@Query("query") String query);

    @GET("/timelines/{id}/events.json")
    Observable<List<EventResource>> getEvents(@Path("id") String timelineId);

    @POST("/timelines/{id}/events.json")
    Observable<EventResource> createEvent(@Path("id") String timelineId, @Body EventRequest request);

    @DELETE("/events/{id}.json")
    Observable<Object> deleteEvent(@Path("id") String eventId);
}
