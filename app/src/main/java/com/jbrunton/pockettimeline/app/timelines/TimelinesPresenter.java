package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.app.shared.BasePresenter;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

public class TimelinesPresenter extends BasePresenter<TimelinesView> {
    private final TimelinesRepository repository;
    private final Navigator navigator;
    private final SchedulerManager schedulerManager;

    public TimelinesPresenter(TimelinesRepository repository, Navigator navigator, SchedulerManager schedulerManager) {
        this.repository = repository;
        this.navigator = navigator;
        this.schedulerManager = schedulerManager;
    }

    @Override public void onResume() {
        withView(LoadingIndicatorView::showLoadingIndicator);
        repository.all()
                .compose(schedulerManager.applySchedulers())
                .subscribe(this::onTimelinesAvailable, this::onError);
    }

    public void showTimelineDetails(Timeline timeline) {
        navigator.startTimelineActivity(timeline.getId());
    }

    private void onTimelinesAvailable(List<Timeline> timelines) {
        withView(view -> view.showTimelines(timelines));
        withView(LoadingIndicatorView::hideLoadingIndicator);
    }

    private void onError(Throwable throwable) {
        withView(view -> view.showMessage("Error: " + throwable.getMessage()));
    }
}
