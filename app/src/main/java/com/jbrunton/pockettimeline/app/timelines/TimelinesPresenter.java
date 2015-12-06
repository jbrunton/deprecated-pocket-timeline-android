package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.app.shared.BasePresenter;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

import javax.inject.Inject;

public class TimelinesPresenter extends BasePresenter<TimelinesView> {
    private final TimelinesProvider provider;
    private final SchedulerManager schedulerManager;

    @Inject public TimelinesPresenter(TimelinesProvider provider, SchedulerManager schedulerManager) {
        this.provider = provider;
        this.schedulerManager = schedulerManager;
    }

    @Override public void onResume() {
        withView(LoadingIndicatorView::showLoadingIndicator);
        provider.getTimelines()
                .compose(schedulerManager.applySchedulers())
                .subscribe(this::onTimelinesAvailable, this::onError);
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        withView(view -> view.showTimelines(timelines));
        withView(LoadingIndicatorView::hideLoadingIndicator);
    }

    private void onError(Throwable throwable) {
        withView(view -> view.showMessage("Error: " + throwable.getMessage()));
    }
}
