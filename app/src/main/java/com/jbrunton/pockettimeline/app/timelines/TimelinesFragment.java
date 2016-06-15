package com.jbrunton.pockettimeline.app.timelines;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.ActivityModule;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorFragment;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

import javax.inject.Inject;

public class TimelinesFragment extends LoadingIndicatorFragment implements TimelinesView {
    @Inject TimelinesPresenter presenter;
    @Inject @PerActivity Navigator navigator;
    private TimelinesAdapter timelinesAdapter;

    private final static String TIMELINES_CACHE_KEY = "timelines";

    @Override protected View createContentView(LayoutInflater inflater, ViewGroup container) {
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
        bind(presenter);
    }

    @Override protected void setupActivityComponent() {
        applicationComponent().activityComponent(
                new ActivityModule(getActivity())
        ).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("Timelines");
    }

    private void showTimeline(Timeline timeline) {
        navigator.startTimelineActivity(timeline.getId());
    }

    @Override public void showTimelines(List<Timeline> timelines) {
        timelinesAdapter.setDataSource(timelines);
    }

}
