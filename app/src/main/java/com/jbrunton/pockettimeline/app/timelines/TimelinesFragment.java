package com.jbrunton.pockettimeline.app.timelines;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

public class TimelinesFragment extends BaseFragment {
    @Inject TimelinesProvider timelinesProvider;
    private TimelinesAdapter timelinesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        timelinesAdapter = new TimelinesAdapter() {
            @Override
            protected void onItemClicked(Timeline timeline) {
                showTimeline(timeline);
            }
        };
        view.setAdapter(timelinesAdapter);

        return view;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        setTitle("Timelines");
        subscribeTo(timelinesProvider.getTimelines(),
                this::onTimelinesAvailable);
    }

    private void showTimeline(Timeline timeline) {
        TimelineActivity.start(getActivity(), timeline.getId());
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        timelinesAdapter.setDataSource(timelines);
    }

}
