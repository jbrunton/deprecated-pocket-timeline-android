package com.jbrunton.pockettimeline.app.search;

import android.support.v7.widget.RecyclerView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.fixtures.EventFactory;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestAppRule;
import com.jbrunton.pockettimeline.fixtures.shadows.ShadowRecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.jbrunton.pockettimeline.fixtures.ViewFixtures.getText;
import static com.jbrunton.pockettimeline.fixtures.shadows.ShadowRecyclerView.shadowOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@Config(shadows={ShadowRecyclerView.class})
@RunWith(RobolectricGradleTestRunner.class)
public class SearchFragmentTest extends FragmentTestSuite<SearchFragment> {
    public static final Event EVENT_ONE = EventFactory.create();
    public static final Event EVENT_TWO = EventFactory.create();

    @Rule public final TestAppRule rule = new TestAppRule();
    @Mock SearchPresenter presenter;

    @Before public void setUp() {
        configureTestSuite(new SearchFragment());
        controller().start().resume();
    }

    @Test public void shouldBindPresenter() {
        verify(presenter).bind(fragment());
    }

    @Test public void shouldDisplayResults() {
        fragment().showResults(asList(EVENT_ONE, EVENT_TWO));
        shadowOf(eventsView()).populateItems();

        assertThat(getText(eventsView().getChildAt(0), R.id.event_title)).isEqualTo(EVENT_ONE.getTitle());
        assertThat(getText(eventsView().getChildAt(1), R.id.event_title)).isEqualTo(EVENT_TWO.getTitle());
    }

    private RecyclerView eventsView() {
        return (RecyclerView) fragment().getView().findViewById(R.id.recycler_view);
    }
}