package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.api.providers.TimelinesProvider;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

public class TimelinesPresenter {
    private TimelinesView view;
    private final TimelinesProvider provider;

    @Inject public TimelinesPresenter(TimelinesProvider provider) {
        this.provider = provider;
    }

    public void bind(TimelinesView view) {
        this.view = view;
    }

    public void onResume() {
        view.showLoadingIndicator();
        provider.getTimelines().subscribe(this::onTimelinesAvailable);
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
