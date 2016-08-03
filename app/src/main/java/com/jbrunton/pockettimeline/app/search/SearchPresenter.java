package com.jbrunton.pockettimeline.app.search;

import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.app.shared.BasePresenter;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.entities.models.Event;

import java.util.List;

public class SearchPresenter extends BasePresenter<SearchView> {
    private final EventsRepository repository;
    private final SchedulerManager schedulerManager;

    public SearchPresenter(EventsRepository repository, SchedulerManager schedulerManager) {
        this.repository = repository;
        this.schedulerManager = schedulerManager;
    }

    public void performSearch(String query) {
        withView(LoadingIndicatorView::showLoadingIndicator);
        repository.search(query)
                .compose(schedulerManager.applySchedulers())
                .subscribe(this::onResultsAvailable, this::onError);
    }

    private void onResultsAvailable(List<Event> events) {
        withView(view -> view.showResults(events));
        withView(LoadingIndicatorView::hideLoadingIndicator);
    }

    private void onError(Throwable throwable) {
        withView(view -> view.showMessage("Error: " + throwable.getMessage()));
    }
}
