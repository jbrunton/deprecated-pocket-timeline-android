package com.jbrunton.pockettimeline.app.shared;

import com.google.common.base.Optional;

import rx.functions.Action1;

public class BasePresenter<ViewType> {
    public static final BasePresenter NULL_PRESENTER = new BasePresenter();

    private ViewType view;

    public void bind(ViewType view) {
        this.view = view;
    }

    public void onResume() {
        // do nothing by default
    }

    public void detach() {
        this.view = null;
    }

    protected Optional<ViewType> getView() {
        return Optional.fromNullable(view);
    }

    protected void withView(Action1<ViewType> action) {
        for (ViewType v : getView().asSet()) {
            action.call(v);
        }
    }
}
