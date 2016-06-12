package com.jbrunton.pockettimeline.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jbrunton.pockettimeline.app.timelines.Henson;

public class Navigator {
    private final Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public static Intent buildTimelineActivityIntent(Context context, String timelineId) {
        return Henson.with(context)
                .gotoTimelineActivity()
                .timelineId(timelineId)
                .build();
    }

    public static Intent buildAddEventActivityIntent(Context context, String timelineId) {
        return Henson.with(context)
                .gotoAddEventActivity()
                .timelineId(timelineId)
                .build();
    }

    public void startTimelineActivity(String timelineId) {
        Intent intent = buildTimelineActivityIntent(activity, timelineId);
        activity.startActivity(intent);
    }

    public void startAddEventActivityForResult(String timelineId, int requestCode) {
        Intent intent = buildAddEventActivityIntent(activity, timelineId);
        activity.startActivityForResult(intent, requestCode);
    }
}
