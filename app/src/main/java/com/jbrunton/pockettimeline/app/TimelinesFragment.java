package com.jbrunton.pockettimeline.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.DaggerProvidersComponent;
import com.jbrunton.pockettimeline.api.ProvidersComponent;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.app.shared.TextViewRecyclerAdapter;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TimelinesFragment extends BaseFragment {
    final ProvidersComponent providers = DaggerProvidersComponent.create();
    private TextViewRecyclerAdapter<Timeline> timelinesAdapter;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        timelinesAdapter = new TextViewRecyclerAdapter<Timeline>() {
            @Override
            protected void onItemClicked(Timeline timeline) {
                showTimeline(timeline);
            }
        };
        view.setAdapter(timelinesAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setTitle("Timelines");
        subscribeTo(providers.timelinesProvider().getTimelines(),
                this::onTimelinesAvailable);
    }

    private void showTimeline(Timeline timeline) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder, TimelineFragment.newInstance(timeline.getId()))
                .addToBackStack(null)
                .commit();
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        timelinesAdapter.setDataSource(timelines);
    }

}
