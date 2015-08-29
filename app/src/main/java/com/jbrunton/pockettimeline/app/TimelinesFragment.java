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
import com.jbrunton.pockettimeline.app.shared.BaseRecyclerAdapter;
import com.jbrunton.pockettimeline.models.Event;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimelinesFragment extends Fragment {
    final ProvidersComponent providers = DaggerProvidersComponent.create();
    private TimelinesListAdapter timelinesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        timelinesAdapter = new TimelinesListAdapter() {
            @Override
            protected void onItemClicked(Timeline timeline) {
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
        getActivity().setTitle("Timelines");

        providers.timelinesProvider().getTimelines()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTimelinesAvailable, this::onError);
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        timelinesAdapter.setDataSource(timelines);
    }

    private void onError(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    private void showMessage(String text) {
        Toast.makeText(this.getActivity(), text, Toast.LENGTH_LONG).show();
    }

    private static class TimelinesListAdapter extends BaseRecyclerAdapter<Timeline, TimelinesListAdapter.ViewHolder> {
        protected TimelinesListAdapter() {
            super(android.R.layout.simple_list_item_1, new TimelinesListAdapter.ViewHolderFactory());
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ViewHolder(View view) {
                super(view);
                this.textView = (TextView) view;
            }
        }

        public static class ViewHolderFactory implements BaseRecyclerAdapter.ViewHolderFactory<Timeline, ViewHolder> {

            @Override
            public ViewHolder createViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void bindHolder(ViewHolder holder, Timeline item) {
                holder.textView.setText(item.getTitle());
            }
        }
    }
}
