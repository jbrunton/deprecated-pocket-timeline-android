package com.jbrunton.pockettimeline.app.timelines;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.f2prateek.dart.InjectExtra;
import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import javax.inject.Inject;

import rx.Observable;

import static rx.Observable.zip;

public class TimelineActivity extends BaseActivity {
    private static final String TIMELINE_CACHE_KEY = "timeline";
    private static final int ADD_EVENT_REQUEST_CODE = 1;

    @Inject TimelinesRepository timelinesRepository;
    @Inject @PerActivity TimelineEventsRepository eventsRepository;
    @Inject Navigator navigator;

    @InjectExtra String timelineId;

    private EventsAdapter eventsAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        FloatingActionButton addEvent = (FloatingActionButton) findViewById(R.id.add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navigator.startAddEventActivityForResult(TimelineActivity.this, timelineId, ADD_EVENT_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);
    }

    @Override protected void setupActivityComponent() {
        DaggerTimelineActivityComponent.builder()
                .applicationComponent(applicationComponent())
                .timelineActivityModule(new TimelineActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle("Timeline");
        setHomeAsUp(true);

        fetchTimeline(false);
    }

    @Override protected String ownerId() {
        return timelineId;
    }

    private Observable<Timeline> getTimeline() {
        return zip(
                timelinesRepository.find(timelineId),
                eventsRepository.all(),
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
            final String eventId = data.getStringExtra(AddEventActivity.ARG_TIMELINE_ID);
            showMessage("Added event", view -> {
                eventsRepository.delete(eventId)
                        .compose(applySchedulers())
                        .subscribe(x -> fetchTimeline(true));
            });

            invalidate(TIMELINE_CACHE_KEY);
        }
    }

    protected String getTimelineId() {
        return timelineId;
    }
}
