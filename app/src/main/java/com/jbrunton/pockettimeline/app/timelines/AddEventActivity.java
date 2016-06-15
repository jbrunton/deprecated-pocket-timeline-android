package com.jbrunton.pockettimeline.app.timelines;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.f2prateek.dart.InjectExtra;
import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.repositories.TimelineEventsRepository;
import com.jbrunton.pockettimeline.app.ActivityModule;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;
import com.jbrunton.pockettimeline.app.shared.DatePickerWidget;
import com.jbrunton.pockettimeline.entities.models.Event;

import org.joda.time.LocalDate;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import static com.jbrunton.pockettimeline.helpers.StringUtils.nullOrEmpty;

public class AddEventActivity extends BaseActivity {
    private LocalDate eventDate;
    @Bind(R.id.event_title) EditText eventTitleText;
    @Bind(R.id.event_description) EditText eventDescription;
    @Inject @PerActivity TimelineEventsRepository eventsRepository;
    @InjectExtra String timelineId;

    public final static int RESULT_CREATED_EVENT = 1;
    public final static String ARG_TIMELINE_ID = "timelineId";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle("Add Event");

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        DatePickerWidget datePickerWidget = (DatePickerWidget) getSupportFragmentManager().findFragmentById(R.id.date_picker);
        datePickerWidget.setOnDateChangedListener(this::onDateChanged);
    }

    @Override protected void setupActivityComponent() {
        applicationComponent().timelineActivityComponent(
                new TimelineModule(timelineId),
                new ActivityModule(this)
        ).inject(this);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Event event = new Event.Builder()
                    .asNewResource()
                    .title(eventTitleText.getText().toString())
                    .date(eventDate)
                    .description(eventDescription.getText().toString())
                    .build();

            eventsRepository.save(event)
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

    protected String getTimelineId() {
        return getIntent().getStringExtra(ARG_TIMELINE_ID);
    }

}
