package com.jbrunton.pockettimeline.app.shared;

import com.jbrunton.pockettimeline.app.timelines.TimelinesView;

public class BasePresenter<ViewType> {
    private ViewType view;

    public void bind(ViewType view) {
        this.view = view;
    }

    protected ViewType getView() {
        return view;
    }

    public void onResume() {}

    public void detach() {
        this.view = null;
    }
}
