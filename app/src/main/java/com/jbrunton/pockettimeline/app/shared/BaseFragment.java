package com.jbrunton.pockettimeline.app.shared;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends RxFragment {
    @Inject RxCache cache;

    @Override public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) {
            cache.invalidate(getContext());
        }
    }

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

    protected <T> Observable<T> cache(String key, Func0<Observable<T>> factory) {
        return cache.cache(getContext(), key, factory);
    }
}
