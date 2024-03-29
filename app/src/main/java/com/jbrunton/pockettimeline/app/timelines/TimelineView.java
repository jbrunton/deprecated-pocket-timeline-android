package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.app.shared.DisplayErrorView;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.entities.models.Timeline;

public interface TimelineView extends DisplayErrorView, LoadingIndicatorView {
    void showTimeline(Timeline timeline);
}
