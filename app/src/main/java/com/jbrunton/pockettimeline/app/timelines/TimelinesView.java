package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.app.shared.DisplayErrorView;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.entities.models.Timeline;

import java.util.List;

public interface TimelinesView extends LoadingIndicatorView, DisplayErrorView {
    void showTimelines(List<Timeline> timelines);
}
