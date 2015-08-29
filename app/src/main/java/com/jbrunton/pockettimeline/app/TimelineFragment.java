package com.jbrunton.pockettimeline.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.app.shared.BaseRecyclerAdapter;
import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

import javax.inject.Inject;

import rx.Observable;

import static rx.Observable.zip;

public class TimelineFragment extends BaseFragment {
    @Inject TimelinesProvider timelinesProvider;
    @Inject EventsProvider eventsProvider;
    private EventsAdapter eventsAdapter;

    private static final String ARG_TIMELINE_ID = "timelineId";

    public static TimelineFragment newInstance(String timelineId) {
        TimelineFragment fragment = new TimelineFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TIMELINE_ID, timelineId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventsAdapter = new EventsAdapter();
        view.setAdapter(eventsAdapter);

        return view;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle("Timeline");
        setHomeAsUp(true);
        subscribeTo(getTimeline(getTimelineId()), this::onTimelineAvailable);
    }

    private Observable<Timeline> getTimeline(String timelineId) {
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

    public static class EventsAdapter extends BaseRecyclerAdapter<Event, EventsAdapter.ViewHolder> {
        protected EventsAdapter() {
            super(R.layout.item_event, new ViewHolderFactory());
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView dateView;
            private TextView titleView;

            public ViewHolder(View itemView) {
                super(itemView);
                dateView = (TextView) itemView.findViewById(R.id.event_date);
                titleView = (TextView) itemView.findViewById(R.id.event_title);
            }
        }

        public static class ViewHolderFactory implements BaseRecyclerAdapter.ViewHolderFactory<Event, ViewHolder> {
            @Override
            public ViewHolder createViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void bindHolder(ViewHolder holder, Event item) {
                holder.dateView.setText(item.getDate().toString());
                holder.titleView.setText(item.getTitle());
            }
        }

    }
}
