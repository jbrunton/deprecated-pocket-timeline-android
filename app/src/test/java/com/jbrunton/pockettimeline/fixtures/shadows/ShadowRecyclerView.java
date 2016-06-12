package com.jbrunton.pockettimeline.fixtures.shadows;

import android.support.v7.widget.RecyclerView;

import org.robolectric.annotation.Implements;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowViewGroup;

@Implements(RecyclerView.class)
public class ShadowRecyclerView extends ShadowViewGroup {
    public void populateItems() {
        realView.measure(0, 0);
        realView.layout(0, 0, 100, 10000);
    }

    public static ShadowRecyclerView shadowOf(RecyclerView actual) {
        return (ShadowRecyclerView) ShadowExtractor.extract(actual);
    }
}
