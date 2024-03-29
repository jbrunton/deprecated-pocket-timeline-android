package com.jbrunton.pockettimeline.app.timelines;

import android.support.v7.widget.RecyclerView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.entities.models.Timeline;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestAppRule;
import com.jbrunton.pockettimeline.fixtures.TimelineFactory;
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
public class TimelinesFragmentTest extends FragmentTestSuite<TimelinesFragment> {
    @Rule public final TestAppRule rule = new TestAppRule();

    @Mock TimelinesPresenter presenter;

    static final Timeline TIMELINE_ONE = TimelineFactory.create();
    static final Timeline TIMELINE_TWO = TimelineFactory.create();

    @Before public void setUp() {
        configureTestSuite(new TimelinesFragment());
        controller().start().resume();
    }

    @Test public void shouldBindPresenter() {
        verify(presenter).bind(fragment());
    }

    @Test public void shouldDisplayTimelines() {
        fragment().showTimelines(asList(TIMELINE_ONE, TIMELINE_TWO));
        shadowOf(timelinesView()).populateItems();

        assertThat(getText(timelinesView().getChildAt(0), R.id.timeline_title)).isEqualTo(TIMELINE_ONE.getTitle());
        assertThat(getText(timelinesView().getChildAt(1), R.id.timeline_title)).isEqualTo(TIMELINE_TWO.getTitle());
    }

    @Test public void shouldShowTimelineDetails() {
        fragment().showTimelines(asList(TIMELINE_ONE));
        shadowOf(timelinesView()).populateItems();

        TimelinesAdapter.ViewHolder viewHolder = (TimelinesAdapter.ViewHolder) timelinesView().findViewHolderForAdapterPosition(0);
        viewHolder.itemView.performClick();

        verify(presenter).showTimelineDetails(TIMELINE_ONE);
    }

    private RecyclerView timelinesView() {
        return (RecyclerView) fragment().getView().findViewById(R.id.recycler_view);
    }
}
