package com.jbrunton.pockettimeline.app.shared;

import android.app.Activity;
import android.support.design.widget.Snackbar;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public abstract class BaseFragment extends RxFragment {

    private BasePresenter presenter = BasePresenter.NULL_PRESENTER;

    public void showMessage(String text) {
        Snackbar.make(this.getView(), text, Snackbar.LENGTH_LONG).show();
    }

    public void showMessage(String text, String actionLabel, Action0 action) {
        Snackbar.make(getView().findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionLabel, v -> action.call())
                .show();
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        setupActivityComponent();
    }

    protected abstract void setupActivityComponent();

    protected void setTitle(String title) {
        getActivity().setTitle(title);
    }

    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected void defaultErrorHandler(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    protected PocketTimelineApplication getApplication() {
        return ((PocketTimelineApplication) getActivity().getApplication());
    }

    protected ApplicationComponent applicationComponent() {
        return getApplication().component();
    }

    protected void setHomeAsUp(boolean showHomeAsUp) {
        ((BaseActivity) getActivity()).setHomeAsUp(showHomeAsUp);
    }

    protected <ViewType> void bind(BasePresenter<ViewType> presenter) {
        this.presenter = presenter;
        presenter.bind((ViewType) this);
    }

    @Override public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
