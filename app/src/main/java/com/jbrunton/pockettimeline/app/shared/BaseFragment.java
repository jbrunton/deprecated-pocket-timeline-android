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

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {
    private CompositeSubscription subscriptions;

    protected void showMessage(String text) {
        Snackbar.make(this.getView(), text, Snackbar.LENGTH_LONG).show();
    }

    protected void onError(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    protected void setTitle(String title) {
        getActivity().setTitle(title);
    }

    protected void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    protected <T> void subscribeTo(Observable<T> observable, final Action1<? super T> onNext, final Action1<Throwable> onError) {
        Subscription subscription = observable
                .subscribeOn(getApplication().defaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);

        addSubscription(subscription);
    }

    protected <T> void subscribeTo(Observable<T> observable, final Action1<? super T> onNext) {
        subscribeTo(observable, onNext, this::onError);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptions = new CompositeSubscription();
    }

    @Override public void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
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
