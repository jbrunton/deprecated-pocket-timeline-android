package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.shared.BasePresenter;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimelinesPresenter extends BasePresenter<TimelinesView> {
    private final TimelinesProvider provider;
    private final SchedulerManager schedulerManager;

    @Inject public TimelinesPresenter(TimelinesProvider provider, SchedulerManager schedulerManager) {
        this.provider = provider;
        this.schedulerManager = schedulerManager;
    }

    public void onResume() {
        getView().showLoadingIndicator();
        provider.getTimelines()
                .compose(schedulerManager.applySchedulers())
                .subscribe(this::onTimelinesAvailable);
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        getView().showTimelines(timelines);
        getView().hideLoadingIndicator();
    }
}
