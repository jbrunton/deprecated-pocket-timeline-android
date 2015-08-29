package com.jbrunton.pockettimeline.api;

import com.jbrunton.pockettimeline.api.resources.TimelineResource;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface RestService {
    @GET("/timelines.json")
    Observable<List<TimelineResource>> getTimelines();
}
