package com.jbrunton.pockettimeline.api.service;

import com.jbrunton.pockettimeline.PocketTimelineApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class CachingInterceptor implements Interceptor {
    private final PocketTimelineApplication application;
    private static final int ONE_MINUTE = 60;
    private static final int ONE_WEEK = ONE_MINUTE * 60 * 24 * 7;

    CachingInterceptor(PocketTimelineApplication application) {
        this.application = application;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (application.isNetworkAvailable()) {
            request = request.newBuilder().header("Cache-Control", "public, max-age=" + ONE_MINUTE).build();
        } else {
            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + ONE_WEEK).build();
        }
        return chain.proceed(request);
    }
}
