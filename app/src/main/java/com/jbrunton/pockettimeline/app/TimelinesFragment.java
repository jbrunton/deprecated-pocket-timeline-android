package com.jbrunton.pockettimeline.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.DaggerProvidersComponent;
import com.jbrunton.pockettimeline.api.ProvidersComponent;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimelinesFragment extends Fragment {
    final ProvidersComponent providers = DaggerProvidersComponent.create();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timelines, container, false);
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
        showMessage("Timelines: " + timelines.size());
    }

    private void onError(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    private void showMessage(String text) {
        Toast.makeText(this.getActivity(), text, Toast.LENGTH_LONG).show();
    }
}
