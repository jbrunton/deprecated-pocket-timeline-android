package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.api.repositories.TimelinesRepository;
import com.jbrunton.pockettimeline.app.Navigator;
import com.jbrunton.pockettimeline.app.shared.BasePresenter;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.entities.models.InvalidInstantiationException;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

import rx.Observable;

import static rx.Observable.zip;

public class TimelinePresenter extends BasePresenter<TimelineView> {
    private final TimelinesRepository timelinesRepository;
    private final TimelineEventsRepository eventsRepository;
    private final Navigator navigator;
    private final SchedulerManager schedulerManager;

    public TimelinePresenter(TimelinesRepository timelinesRepository, TimelineEventsRepository eventsRepository, Navigator navigator, SchedulerManager schedulerManager) {
        this.timelinesRepository = timelinesRepository;
        this.eventsRepository = eventsRepository;
        this.navigator = navigator;
        this.schedulerManager = schedulerManager;
    }

    @Override public void onResume() {
        super.onResume();
        withView(LoadingIndicatorView::showLoadingIndicator);
        fetchTimeline();
    }

    public void startAddEventActivity(int requestCode) {
        navigator.startAddEventActivityForResult(eventsRepository.timelineId(), requestCode);
    }

    private void fetchTimeline() {
        getTimeline().compose(schedulerManager.applySchedulers())
                .subscribe(this::onTimelineAvailable, this::onError);
    }

    private Observable<Timeline> getTimeline() {
        return zip(
                timelinesRepository.find(eventsRepository.timelineId()),
                eventsRepository.all(),
                this::buildTimeline
        );
    }

    public void deleteEvent(String eventId) {
        eventsRepository.delete(eventId)
                .compose(schedulerManager.applySchedulers())
                .subscribe(x -> this.fetchTimeline(), this::onError);
    }

    public void showUndoForNewEvent(String eventId) {
        withView(view -> view.showMessage(
                "Added event", "Undo",
                () -> deleteEvent(eventId)));
    }

    private void onTimelineAvailable(Timeline timeline) {
        withView(view -> view.showTimeline(timeline));
        withView(LoadingIndicatorView::hideLoadingIndicator);
    }

    private void onError(Throwable throwable) {
        withView(view -> view.showMessage("Error: " + throwable.getMessage()));
    }

    private Timeline buildTimeline(Timeline timeline, List<Event> events) {
        try {
            return timeline.withEvents(events);
        } catch (InvalidInstantiationException e) {
            throw new IllegalStateException(e);
        }
    }
}
