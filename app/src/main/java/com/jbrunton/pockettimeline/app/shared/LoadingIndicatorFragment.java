package com.jbrunton.pockettimeline.app.shared;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbrunton.pockettimeline.R;

public abstract class LoadingIndicatorFragment extends BaseFragment {
    private ViewGroup contentHolder;
    private View loadingIndicator;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View outerView = inflater.inflate(R.layout.fragment_loading_indicator, container, false);
        contentHolder = (ViewGroup) outerView.findViewById(R.id.content_holder);
        loadingIndicator = outerView.findViewById(R.id.progress_bar);

        View content = createContentView(inflater, contentHolder);

        contentHolder.addView(content);
        return outerView;
    }

    protected abstract View createContentView(LayoutInflater inflater, ViewGroup container);

    public void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
        contentHolder.setVisibility(View.GONE);
    }

    public void hideLoadingIndicator() {
        loadingIndicator.setVisibility(View.GONE);
        contentHolder.setVisibility(View.VISIBLE);
    }
}
