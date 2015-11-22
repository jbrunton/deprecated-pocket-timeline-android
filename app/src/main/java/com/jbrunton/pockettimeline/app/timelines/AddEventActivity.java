package com.jbrunton.pockettimeline.app.timelines;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;
import com.jbrunton.pockettimeline.app.shared.DatePickerWidget;
import com.jbrunton.pockettimeline.models.Event;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static android.support.v7.app.AlertDialog.*;
import static com.jbrunton.pockettimeline.helpers.StringUtils.nullOrEmpty;

public class AddEventActivity extends BaseActivity {
    private LocalDate eventDate;
    @Bind(R.id.event_title) EditText eventTitleText;
    @Inject EventsProvider eventsProvider;

    public final static String ARG_TIMELINE_ID = "timelineId";
    public final static int RESULT_CREATED_EVENT = 1;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle("Add Event");

        ButterKnife.bind(this);

        applicationComponent().inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        DatePickerWidget datePickerWidget = (DatePickerWidget) getSupportFragmentManager().findFragmentById(R.id.date_picker);
        datePickerWidget.setOnDateChangedListener(this::onDateChanged);
    }

    public static void startForResult(AppCompatActivity activity, String timelineId, int requestCode) {
        Intent intent = new Intent(activity, AddEventActivity.class);
        intent.putExtra(ARG_TIMELINE_ID, timelineId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            eventsProvider.createEvent(getIntent().getStringExtra(ARG_TIMELINE_ID), eventTitleText.getText().toString(), eventDate)
                    .compose(applySchedulers())
                    .subscribe(this::eventCreated, this::defaultErrorHandler);
        }
        return super.onOptionsItemSelected(item);
    }

    private void eventCreated(Event event) {
        setResult(RESULT_CREATED_EVENT, new Intent().putExtra(ARG_TIMELINE_ID, event.getId()));
        finish();
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        boolean valid = eventDate != null && !nullOrEmpty(eventTitleText.getText());
        menu.getItem(0).setEnabled(valid);
        return true;
    }

    @OnTextChanged(R.id.event_title) void titleChanged(CharSequence title) {
        invalidateOptionsMenu();
    }

    private void onDateChanged(LocalDate date) {
        eventDate = date;
        invalidateOptionsMenu();
    }
}
