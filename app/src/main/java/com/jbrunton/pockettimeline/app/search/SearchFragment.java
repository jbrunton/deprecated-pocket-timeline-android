package com.jbrunton.pockettimeline.app.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.ActivityModule;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.app.timelines.EventsAdapter;
import com.jbrunton.pockettimeline.entities.models.Event;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment implements com.jbrunton.pockettimeline.app.search.SearchView {
    @Inject @PerActivity SearchPresenter presenter;
    private EventsAdapter eventsAdapter;
    private String query;
    private SearchView searchView;
    private final SearchView.OnQueryTextListener onQueryTextListener = new SearchQueryTextListener();

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventsAdapter = new EventsAdapter();
        view.setAdapter(eventsAdapter);

        return view;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(presenter);

        if (savedInstanceState != null) {
            query = savedInstanceState.getString("query");
            presenter.performSearch(query);
        }
    }

    @Override protected void setupActivityComponent() {
        applicationComponent()
                .activityComponent(new ActivityModule(getActivity()))
                .inject(this);
    }

    @Override public void onResume() {
        super.onResume();
        setTitle("Search");
        setHasOptionsMenu(true);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("query", query);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        if (query != null) {
            searchMenuItem.expandActionView();
            searchView.setQuery(query, false);
        }
        searchView.setOnQueryTextListener(onQueryTextListener);
    }

    @Override
    public void showResults(List<Event> events) {
        eventsAdapter.setDataSource(events);
    }

    private class SearchQueryTextListener implements SearchView.OnQueryTextListener {
        @Override public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override public boolean onQueryTextChange(String query) {
            searchFor(query);
            return true;
        }

        private void searchFor(String query) {
            SearchFragment.this.query = query;
            eventsAdapter.setDataSource(Collections.<Event>emptyList());
            presenter.performSearch(query);
        }
    };
}
