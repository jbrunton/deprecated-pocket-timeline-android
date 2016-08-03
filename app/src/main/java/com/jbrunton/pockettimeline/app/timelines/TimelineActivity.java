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
    private static final int ADD_EVENT_REQUEST_CODE = 1;

    @Inject @PerActivity TimelinePresenter presenter;
    @Inject Navigator navigator;

    @InjectExtra String timelineId;

    private EventsAdapter eventsAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(presenter);
    }

    @Override protected View createContentView(ViewGroup root) {
        View view = getLayoutInflater().inflate(R.layout.activity_timeline, root, false);

        FloatingActionButton addEvent = (FloatingActionButton) view.findViewById(R.id.add_event);
        addEvent.setOnClickListener(this::onAddEventClicked);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);

        return view;
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
            presenter.showUndoForNewEvent(eventId);
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

    protected void onAddEventClicked(View view) {
        presenter.startAddEventActivity(ADD_EVENT_REQUEST_CODE);
    }
}
