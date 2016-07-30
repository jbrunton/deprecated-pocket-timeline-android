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

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.app.timelines.EventsAdapter;
import com.jbrunton.pockettimeline.entities.models.Event;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class SearchFragment extends BaseFragment {
    @Inject EventsRepository eventsRepository;
    private EventsAdapter eventsAdapter;
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

        if (savedInstanceState != null) {
            query = savedInstanceState.getString("query");
        }
    }

    @Override protected void setupActivityComponent() {
        applicationComponent().inject(this);
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

    private void searchFor(String query) {
        this.query = query;

        eventsAdapter.setDataSource(Collections.<Event>emptyList());

        doSearch(query)
                .compose(applySchedulers())
                .subscribe(this::searchResultsAvailable, this::defaultErrorHandler);
    }

    private Observable<List<Event>> doSearch(String query) {
        return eventsRepository.search(query);
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
