package com.jbrunton.pockettimeline.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.DaggerProvidersComponent;
import com.jbrunton.pockettimeline.api.ProvidersComponent;
import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static rx.Observable.zip;

public class TimelineFragment extends Fragment {
    final ProvidersComponent providers = DaggerProvidersComponent.create();
    private EventsListAdapter eventsAdapter;

    public static TimelineFragment newInstance(String timelineId) {
        TimelineFragment fragment = new TimelineFragment();

        Bundle args = new Bundle();
        args.putString("timelineId", timelineId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventsAdapter = new EventsListAdapter();
        view.setAdapter(eventsAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Timeline");

        getTimeline(getTimelineId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTimelineAvailable, this::onError);
    }

    private Observable<Timeline> getTimeline(String timelineId) {
        return zip(
                providers.timelinesProvider().getTimeline(timelineId),
                providers.eventsProvider().getEvents(timelineId),
                Timeline::withEvents
        );
    }

    private void onTimelineAvailable(Timeline timeline) {
        getActivity().setTitle(timeline.getTitle());
        eventsAdapter.setData(timeline.getEvents());
    }

    private void onError(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    private void showMessage(String text) {
        Toast.makeText(this.getActivity(), text, Toast.LENGTH_LONG).show();
    }

    private String getTimelineId() {
        return getArguments().getString("timelineId");
    }

    private static class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {
        private List<Event> events = new ArrayList<>();

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ViewHolder(TextView textView) {
                super(textView);
                this.textView = textView;
            }
        }

        @Override
        public EventsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);

            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(EventsListAdapter.ViewHolder holder, int position) {
            holder.textView.setText(events.get(position).getTitle());
            holder.textView.setOnClickListener(v -> {

            });
        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        public void setData(Collection<Event> events) {
            this.events = new ArrayList<>(events);
            notifyDataSetChanged();
        }
    }
}
