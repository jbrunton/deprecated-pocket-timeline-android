package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimelinesPresenter {
    private TimelinesView view;
    private final TimelinesProvider provider;
    private final SchedulerManager schedulerManager;

    @Inject public TimelinesPresenter(TimelinesProvider provider, SchedulerManager schedulerManager) {
        this.provider = provider;
        this.schedulerManager = schedulerManager;
    }

    public void bind(TimelinesView view) {
        this.view = view;
    }

    public void onResume() {
        view.showLoadingIndicator();
        provider.getTimelines()
                .compose(schedulerManager.applySchedulers())
                .subscribe(this::onTimelinesAvailable);
    }

    public void detach() {
        this.view = null;
    }

    protected TimelinesView getView() {
        return view;
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        view.showTimelines(timelines);
        view.hideLoadingIndicator();
    }
}
