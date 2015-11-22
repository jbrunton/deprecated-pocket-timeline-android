package com.jbrunton.pockettimeline.app.shared;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.jbrunton.pockettimeline.PocketTimelineApplication;
import com.jbrunton.pockettimeline.app.ApplicationComponent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class BaseActivity extends RxCacheActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onUpPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    public void setHomeAsUp(boolean showHomeAsUp) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        actionBar.setDisplayShowHomeEnabled(showHomeAsUp);
    }

    protected void onUpPressed() {
        finish();
    }

    protected ApplicationComponent applicationComponent() {
        return ((PocketTimelineApplication) getApplication()).component();
    }

    protected void showMessage(String text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .show();
    }

    protected void showMessage(String text, View.OnClickListener action) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction("Undo", action)
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
