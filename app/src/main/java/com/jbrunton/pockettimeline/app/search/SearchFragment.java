package com.jbrunton.pockettimeline.app.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.app.timelines.EventsAdapter;
import com.jbrunton.pockettimeline.models.Event;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class SearchFragment extends BaseFragment {
    @Inject EventsProvider eventsProvider;
    private EventsAdapter eventsAdapter;
    private final String SEARCH_CACHE_KEY = "search";
    private String query;
    private SearchView searchView;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        view.setLayoutManager(new LinearLayoutManager(getActivity()));

        eventsAdapter = new EventsAdapter();
        view.setAdapter(eventsAdapter);

        return view;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationComponent().inject(this);

        if (savedInstanceState != null) {
            query = savedInstanceState.getString("query");
        }
    }

    @Override public void onResume() {
        super.onResume();
        setTitle("Search");
        setHasOptionsMenu(true);

        subscribeTo(fetch(SEARCH_CACHE_KEY),
                this::searchResultsAvailable);
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

    private void searchFor(String query) {
        this.query = query;

        eventsAdapter.setDataSource(Collections.<Event>emptyList());

        invalidate(SEARCH_CACHE_KEY);
        subscribeTo(cache(SEARCH_CACHE_KEY, () -> doSearch(query)),
                this::searchResultsAvailable);
    }

    private Observable<List<Event>> doSearch(String query) {
        return eventsProvider.searchEvents(query);
    }

    private void searchResultsAvailable(List<Event> events) {
        eventsAdapter.setDataSource(events);
    }

    private final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override public boolean onQueryTextChange(String query) {
            searchFor(query);
            return true;
        }
    };
}
