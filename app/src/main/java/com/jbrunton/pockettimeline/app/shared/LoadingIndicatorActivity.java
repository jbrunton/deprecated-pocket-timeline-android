package com.jbrunton.pockettimeline.app.shared;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.jbrunton.pockettimeline.R;

public abstract class LoadingIndicatorActivity extends BaseActivity implements LoadingIndicatorView {
    private ViewGroup contentHolder;
    private View loadingIndicator;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loading_indicator);
        contentHolder = (ViewGroup) findViewById(R.id.content_holder);
        loadingIndicator = findViewById(R.id.progress_bar);

        createContentView(contentHolder);
    }

    protected abstract void createContentView(ViewGroup container);

    @Override public void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
        contentHolder.setVisibility(View.GONE);
    }

    @Override public void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.GONE);
        contentHolder.setVisibility(View.VISIBLE);
    }
}
