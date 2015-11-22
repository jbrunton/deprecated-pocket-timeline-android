package com.jbrunton.pockettimeline.app.timelines;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

public class AddEventActivity extends BaseActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle("Add Event");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
    }

    public static void start(Context context, String timelineId) {
        Intent intent = new Intent(context, AddEventActivity.class);
        // intent.putExtra(ARG_TIMELINE_ID, timelineId);
        context.startActivity(intent);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }
}
