package com.jbrunton.pockettimeline.app.shared;

import com.google.common.base.Optional;
import com.jbrunton.pockettimeline.app.timelines.TimelinesView;

import rx.functions.Action1;

public class BasePresenter<ViewType> {
    private ViewType view;

    public void bind(ViewType view) {
        this.view = view;
    }

    protected Optional<ViewType> getView() {
        return Optional.fromNullable(view);
    }

    protected void withView(Action1<ViewType> action) {
        for (ViewType view : getView().asSet()) {
            action.call(view);
        }
    }

    public void onResume() {}

    public void detach() {
        this.view = null;
    }
}
