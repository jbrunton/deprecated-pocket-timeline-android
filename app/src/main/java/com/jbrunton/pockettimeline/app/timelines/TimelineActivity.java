package com.jbrunton.pockettimeline.app.timelines;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;
import com.jbrunton.pockettimeline.models.Timeline;

import javax.inject.Inject;

import rx.Observable;

import static rx.Observable.zip;

public class TimelineActivity extends BaseActivity {
    private static final String ARG_TIMELINE_ID = "timelineId";
    private static final String TIMELINE_CACHE_KEY = "timeline";

    @Inject TimelinesProvider timelinesProvider;
    @Inject EventsProvider eventsProvider;
    private EventsAdapter eventsAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        FloatingActionButton addEvent = (FloatingActionButton) findViewById(R.id.add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AddEventActivity.start(TimelineActivity.this, getTimelineId());
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);

        applicationComponent().inject(this);
    }

    public static void start(Context context, String timelineId) {
        Intent intent = new Intent(context, TimelineActivity.class);
        intent.putExtra(ARG_TIMELINE_ID, timelineId);
        context.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle("Timeline");
        setHomeAsUp(true);

        cache(TIMELINE_CACHE_KEY, this::getTimeline)
                .compose(applySchedulers())
                .subscribe(this::onTimelineAvailable, this::defaultErrorHandler);
    }

    @Override protected String ownerId() {
        return getTimelineId();
    }

    private Observable<Timeline> getTimeline() {
        String timelineId = getTimelineId();
        return zip(
                timelinesProvider.getTimeline(timelineId),
                eventsProvider.getEvents(timelineId),
                Timeline::withEvents
        );
    }

    private void onTimelineAvailable(Timeline timeline) {
        setTitle(timeline.getTitle());
        eventsAdapter.setDataSource(timeline.getEvents());
    }

    private String getTimelineId() {
        return getIntent().getStringExtra(ARG_TIMELINE_ID);
    }
}
