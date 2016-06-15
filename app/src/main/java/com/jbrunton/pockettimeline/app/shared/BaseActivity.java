package com.jbrunton.pockettimeline.app.shared;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.f2prateek.dart.Dart;
import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public abstract class BaseActivity extends RxCacheActivity {
    private BasePresenter presenter;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onUpPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);
        setupActivityComponent();
    }

    @Override public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detach();
        }
    }

    public void setHomeAsUp(boolean showHomeAsUp) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        actionBar.setDisplayShowHomeEnabled(showHomeAsUp);
    }

    protected abstract void setupActivityComponent();

    protected <ViewType> void bind(BasePresenter<ViewType> presenter) {
        this.presenter = presenter;
        presenter.bind((ViewType) this);
    }

    protected void onUpPressed() {
        finish();
    }

    protected ApplicationComponent applicationComponent() {
        return ((PocketTimelineApplication) getApplication()).component();
    }

    public void showMessage(String text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .show();
    }

    public void showMessage(String text, String actionLabel, Action0 action) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionLabel, v -> action.call())
                .show();
    }

    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected void defaultErrorHandler(Throwable throwable) {
        showMessage("Error: " + throwable.getMessage());
    }

}
