package com.jbrunton.pockettimeline.app.shared;

import android.support.design.widget.Snackbar;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BaseFragment extends RxCacheFragment {

    protected void showMessage(String text) {
        Snackbar.make(this.getView(), text, Snackbar.LENGTH_LONG).show();
    }

    protected void onError(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    protected void setTitle(String title) {
        getActivity().setTitle(title);
    }

    protected <T> void subscribeTo(Observable<T> observable, final Action1<? super T> onNext, final Action1<Throwable> onError) {
        observable
                .subscribeOn(getApplication().defaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(onNext, onError);
    }

    protected <T> void subscribeTo(Observable<T> observable, final Action1<? super T> onNext) {
        subscribeTo(observable, onNext, this::onError);
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
