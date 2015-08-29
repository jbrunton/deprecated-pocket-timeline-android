package com.jbrunton.pockettimeline.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;
import com.jbrunton.pockettimeline.app.shared.BaseRecyclerAdapter;

public class TimelineActivity extends BaseActivity {
    private static final String ARG_TIMELINE_ID = "timelineId";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        if (savedInstanceState == null) {
            String timelineId = getIntent().getStringExtra(ARG_TIMELINE_ID);
            TimelineFragment fragment = TimelineFragment.newInstance(timelineId);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, fragment)
                    .commit();
        }
    }

    public static void start(Context context, String timelineId) {
        Intent intent = new Intent(context, TimelineActivity.class);
        intent.putExtra(ARG_TIMELINE_ID, timelineId);
        context.startActivity(intent);
    }
}
