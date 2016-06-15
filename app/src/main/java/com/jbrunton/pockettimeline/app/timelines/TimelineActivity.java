package com.jbrunton.pockettimeline.app.timelines;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.f2prateek.dart.InjectExtra;
import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.ActivityModule;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorActivity;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import javax.inject.Inject;

public class TimelineActivity extends LoadingIndicatorActivity implements TimelineView {
    private static final String TIMELINE_CACHE_KEY = "timeline";
    private static final int ADD_EVENT_REQUEST_CODE = 1;

    @Inject @PerActivity TimelinePresenter presenter;
    @Inject Navigator navigator;

    @InjectExtra String timelineId;

    private EventsAdapter eventsAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        FloatingActionButton addEvent = (FloatingActionButton) findViewById(R.id.add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navigator.startAddEventActivityForResult(timelineId, ADD_EVENT_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);

        bind(presenter);
    }

    @Override protected void createContentView(ViewGroup root) {
        getLayoutInflater().inflate(R.layout.activity_timeline, root);
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle("Timeline");
        setHomeAsUp(true);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AddEventActivity.RESULT_CREATED_EVENT) {
            final String eventId = data.getStringExtra(AddEventActivity.ARG_TIMELINE_ID);
            showMessage("Added event", view -> {
                /*eventsRepository.delete(eventId)
                        .compose(applySchedulers())
                        .subscribe(x -> fetchTimeline(true));*/
            });

            invalidate(TIMELINE_CACHE_KEY);
        }
    }


    @Override public void showTimeline(Timeline timeline) {
        setTitle(timeline.getTitle());
        eventsAdapter.setDataSource(timeline.getEvents());
    }

    @Override protected void setupActivityComponent() {
        applicationComponent().timelineActivityComponent(
                new TimelineModule(timelineId),
                new ActivityModule(this)
        ).inject(this);
    }

    protected String getTimelineId() {
        return timelineId;
    }
}
