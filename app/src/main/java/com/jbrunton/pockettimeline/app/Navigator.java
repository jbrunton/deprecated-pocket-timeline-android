package com.jbrunton.pockettimeline.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.jbrunton.pockettimeline.app.timelines.Henson;

public class Navigator {
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

    public void startTimelineActivity(Context context, String timelineId) {
        Intent intent = buildTimelineActivityIntent(context, timelineId);
        context.startActivity(intent);
    }

    public void startAddEventActivityForResult(AppCompatActivity activity, String timelineId, int requestCode) {
        Intent intent = buildAddEventActivityIntent(activity, timelineId);
        activity.startActivityForResult(intent, requestCode);
    }
}
