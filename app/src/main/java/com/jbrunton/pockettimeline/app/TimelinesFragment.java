package com.jbrunton.pockettimeline.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_timelines, container, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        view.setHasFixedSize(true);

        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        timelinesAdapter = new TimelinesListAdapter();
        view.setAdapter(timelinesAdapter);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        providers.timelinesProvider().getTimelines()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (List<Timeline> timelines) -> onTimelinesAvailable(timelines),
                        throwable -> onError(throwable)
                );
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

    private static class TimelinesListAdapter extends RecyclerView.Adapter<TimelinesListAdapter.ViewHolder> {
        private List<Timeline> timelines = new ArrayList<>();

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

            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(TimelinesListAdapter.ViewHolder holder, int position) {
            holder.textView.setText(timelines.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return timelines.size();
        }

        public void setData(Collection<Timeline> timelines) {
            this.timelines = new ArrayList<>(timelines);
            notifyDataSetChanged();
        }
    }
}
