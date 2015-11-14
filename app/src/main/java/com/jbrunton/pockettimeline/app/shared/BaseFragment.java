package com.jbrunton.pockettimeline.app.shared;

import android.support.design.widget.Snackbar;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BaseFragment extends RxCacheFragment {

    protected void showMessage(String text) {
        Snackbar.make(this.getView(), text, Snackbar.LENGTH_LONG).show();
    }

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
}
