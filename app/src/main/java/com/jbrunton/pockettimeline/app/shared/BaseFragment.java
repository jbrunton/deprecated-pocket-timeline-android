package com.jbrunton.pockettimeline.app.shared;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    protected void showMessage(String text) {
        Snackbar.make(this.getView(), text, Snackbar.LENGTH_LONG).show();
    }

    protected void onError(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

    protected void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    protected <T> void subscribeTo(Observable<T> observable, final Action1<? super T> onNext, final Action1<Throwable> onError) {
        Subscription subscription = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);

        addSubscription(subscription);
    }

    protected <T> void subscribeTo(Observable<T> observable, final Action1<? super T> onNext) {
        subscribeTo(observable, onNext, this::onError);
    }

    @Override
    public void onDestroy() {
        subscriptions.unsubscribe();
        super.onDestroy();
    }
}
