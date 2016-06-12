package com.jbrunton.pockettimeline.app;

import android.content.Context;
import android.content.Intent;

import com.jbrunton.pockettimeline.app.timelines.Henson;

public class Navigator {
    public static Intent buildTimelineActivityIntent(Context context, String timelineId) {
        return Henson.with(context)
                .gotoTimelineActivity()
                .timelineId(timelineId)
                .build();
    }

    public void startTimelineActivity(Context context, String timelineId) {
        Intent intent = buildTimelineActivityIntent(context, timelineId);
        context.startActivity(intent);
    }
}
