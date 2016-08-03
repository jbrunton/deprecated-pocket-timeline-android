package com.jbrunton.pockettimeline.app.search;

import com.jbrunton.pockettimeline.app.shared.DisplayErrorView;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.entities.models.Event;

import java.util.List;

public interface SearchView extends DisplayErrorView, LoadingIndicatorView {
    void showResults(List<Event> results);
}
