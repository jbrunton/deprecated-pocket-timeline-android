package com.jbrunton.pockettimeline.app.timelines;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.models.Timeline;

import java.sql.Time;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;

import static rx.Observable.zip;

public class TimelineFragment extends BaseFragment {
    @Inject TimelinesProvider timelinesProvider;
    @Inject EventsProvider eventsProvider;
    private EventsAdapter eventsAdapter;

    private static final String ARG_TIMELINE_ID = "timelineId";
    private static final String TIMELINE_CACHE_KEY = "timeline";

    public static TimelineFragment newInstance(String timelineId) {
        TimelineFragment fragment = new TimelineFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TIMELINE_ID, timelineId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        FloatingActionButton addEvent = (FloatingActionButton) view.findViewById(R.id.add_event);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showMessage("Hey");
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventsAdapter = new EventsAdapter();
        recyclerView.setAdapter(eventsAdapter);

        return view;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationComponent().inject(this);
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
        getActivity().setTitle(timeline.getTitle());
        eventsAdapter.setDataSource(timeline.getEvents());
    }

    private String getTimelineId() {
        return getArguments().getString(ARG_TIMELINE_ID);
    }

}
