package com.jbrunton.pockettimeline.app.timelines;

import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

public interface TimelinesView extends LoadingIndicatorView {
    void showTimelines(List<Timeline> timelines);
}
