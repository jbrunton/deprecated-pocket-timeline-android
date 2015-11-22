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
import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

import javax.inject.Inject;

import rx.Observable;

import static rx.Observable.zip;

public class TimelineActivity extends BaseActivity {
    private static final String ARG_TIMELINE_ID = "timelineId";
    private static final String TIMELINE_CACHE_KEY = "timeline";
    private static final int ADD_EVENT_REQUEST_CODE = 1;

    @Inject TimelinesProvider timelinesProvider;
    @Inject EventsProvider eventsProvider;
    private EventsAdapter eventsAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        FloatingActionButton addEvent = (FloatingActionButton) findViewById(R.id.add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AddEventActivity.startForResult(TimelineActivity.this, getTimelineId(), ADD_EVENT_REQUEST_CODE);
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

        fetchTimeline(false);
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

    private Observable<Timeline> fetchTimeline(boolean invalidate) {
        if (invalidate) {
            invalidate(TIMELINE_CACHE_KEY);
        }

        Observable<Timeline> timeline = cache(TIMELINE_CACHE_KEY, this::getTimeline)
                .compose(applySchedulers());
        timeline.subscribe(this::onTimelineAvailable, this::defaultErrorHandler);

        return timeline;
    }

    private void onTimelineAvailable(Timeline timeline) {
        setTitle(timeline.getTitle());
        eventsAdapter.setDataSource(timeline.getEvents());
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AddEventActivity.RESULT_CREATED_EVENT) {
            final String eventId = data.getStringExtra("timelineId");
            showMessage("Added event", view -> {
                eventsProvider.deleteEvent(eventId)
                        .compose(applySchedulers())
                        .subscribe(x -> fetchTimeline(true));
            });

            invalidate(TIMELINE_CACHE_KEY);
        }
    }

    private String getTimelineId() {
        return getIntent().getStringExtra(ARG_TIMELINE_ID);
    }
}
