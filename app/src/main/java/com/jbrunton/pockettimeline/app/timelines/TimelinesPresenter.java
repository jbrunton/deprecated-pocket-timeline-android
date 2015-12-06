package com.jbrunton.pockettimeline.app.timelines;

import javax.inject.Inject;

public class TimelinesPresenter {
    private TimelinesView view;

    @Inject public TimelinesPresenter() {

    }

    public void bind(TimelinesView view) {
        this.view = view;
    }

    public void detach() {
        this.view = null;
    }

    protected TimelinesView getView() {
        return view;
    }
}
