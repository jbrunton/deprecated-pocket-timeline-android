package com.jbrunton.pockettimeline.app.timelines;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static android.support.v7.app.AlertDialog.*;

public class AddEventActivity extends BaseActivity {
    private final String[] MONTHS = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Bind(R.id.month_of_year) EditText monthPicker;
    @Bind(R.id.day_of_month) EditText dayPicker;
    @Bind(R.id.year) EditText yearPicker;

    @Bind(R.id.day_of_month_wrapper) TextInputLayout dayPickerWrapper;
    @Bind(R.id.year_wrapper) TextInputLayout yearPickerWrapper;

    private LocalDate eventDate;
    private Integer dayOfMonth;
    private Integer monthOfYear;
    private Integer year;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle("Add Event");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        ButterKnife.bind(this);
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

    @OnClick(R.id.month_of_year) void pickMonth(View view) {
        Builder builder = new Builder(view.getContext());
        builder.setTitle("Month");
        builder.setItems(MONTHS, this::onMonthChanged);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    @OnTextChanged(R.id.day_of_month) void onDayChanged(CharSequence text) {
        dayPickerWrapper.setError(null);
        if (text.toString().matches("\\d+")) {
            dayOfMonth = Integer.valueOf(text.toString());
            if (dayOfMonth <= 31) {
                validateDate();
            } else {
                dayPickerWrapper.setError("Invalid day");
            }
        } else {
            dayPickerWrapper.setError("Invalid day");
        }
    }

    void onMonthChanged(DialogInterface dialogInterface, int selectedIndex) {
        monthOfYear = selectedIndex + 1;
        monthPicker.setText(MONTHS[selectedIndex]);
        validateDate();
    }

    @OnTextChanged(R.id.year) void onYearChanged(CharSequence text) {
        dayPickerWrapper.setError(null);
        yearPickerWrapper.setError(null);
        if (text.toString().matches("\\d+")) {
            year = Integer.valueOf(text.toString());
            if (year <= LocalDate.now().getYear()) {
                validateDate();
            } else {
                dayPickerWrapper.setError("Invalid year");
            }
        } else {
            yearPickerWrapper.setError("Invalid year");
        }
    }

    private void validateDate() {
        if (dayOfMonth != null && monthOfYear != null && year != null) {
            try {
                eventDate = new LocalDate(year, monthOfYear, dayOfMonth);
            } catch (IllegalFieldValueException e) {
                dayPickerWrapper.setError("Invalid day");
            }
        }
    }
}
