package com.jbrunton.pockettimeline.app;

import android.os.Bundle;
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
import com.jbrunton.pockettimeline.models.Timeline;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimelinesFragment extends Fragment {
    final ProvidersComponent providers = DaggerProvidersComponent.create();
    private TimelinesListAdapter timelinesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        view.setHasFixedSize(true);

        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        timelinesAdapter = new TimelinesListAdapter() {
            @Override
            protected void onTimelineClicked(Timeline timeline) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_holder, TimelineFragment.newInstance(timeline.getId()))
                        .addToBackStack(null)
                        .commit();
            }
        };
        view.setAdapter(timelinesAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        providers.timelinesProvider().getTimelines()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTimelinesAvailable, this::onError);
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        timelinesAdapter.setData(timelines);
    }

    private void onError(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    private void showMessage(String text) {
        Toast.makeText(this.getActivity(), text, Toast.LENGTH_LONG).show();
    }

    private static abstract class TimelinesListAdapter extends RecyclerView.Adapter<TimelinesListAdapter.ViewHolder> {
        private List<Timeline> timelines = new ArrayList<>();

        private final View.OnClickListener itemClicked = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timeline timeline = (Timeline) v.getTag();
                onTimelineClicked(timeline);
            }
        };

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ViewHolder(TextView textView) {
                super(textView);
                this.textView = textView;
            }
        }

        @Override
        public TimelinesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            textView.setOnClickListener(itemClicked);

            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(TimelinesListAdapter.ViewHolder holder, int position) {
            Timeline timeline = timelines.get(position);
            holder.textView.setText(timeline.getTitle());
            holder.textView.setTag(timeline);
        }

        @Override
        public int getItemCount() {
            return timelines.size();
        }

        public void setData(Collection<Timeline> timelines) {
            this.timelines = new ArrayList<>(timelines);
            notifyDataSetChanged();
        }

        protected abstract void onTimelineClicked(Timeline timeline);
    }
}
