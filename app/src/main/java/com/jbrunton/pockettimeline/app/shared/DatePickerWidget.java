package com.jbrunton.pockettimeline.app.shared;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jbrunton.pockettimeline.R;

import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.functions.Func1;

public class DatePickerWidget extends Fragment {
    public interface OnDateChangedListener {
        void onDateChanged(LocalDate date);
    }

    private final String[] MONTHS = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private OnDateChangedListener onDateChangedListener;

    @Bind(R.id.month_of_year) EditText monthPicker;
    @Bind(R.id.day_of_month) EditText dayPicker;
    @Bind(R.id.year) EditText yearPicker;

    @Bind(R.id.day_of_month_wrapper) TextInputLayout dayPickerWrapper;
    @Bind(R.id.year_wrapper) TextInputLayout yearPickerWrapper;

    private LocalDate selectedDate;
    private Integer dayOfMonth;
    private Integer monthOfYear;
    private Integer year;

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.onDateChangedListener = onDateChangedListener;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_date_picker, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.month_of_year) void pickMonth(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Month");
        builder.setItems(MONTHS, this::onMonthChanged);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    @OnTextChanged(R.id.day_of_month) void onDayChanged(CharSequence text) {
        dayPickerWrapper.setError(null);
        if (validNumber(text, dayOfMonth -> dayOfMonth <= 31)) {
            dayOfMonth = Integer.valueOf(text.toString());
            validateDate();
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
        if (validNumber(text, year -> year <= LocalDate.now().getYear())) {
            year = Integer.valueOf(text.toString());
            validateDate();
        } else {
            yearPickerWrapper.setError("Invalid year");
        }

    }

    private void validateDate() {
        if (dayOfMonth != null && monthOfYear != null && year != null) {
            try {
                selectedDate = new LocalDate(year, monthOfYear, dayOfMonth);
                if (onDateChangedListener != null) {
                    onDateChangedListener.onDateChanged(selectedDate);
                }
            } catch (IllegalFieldValueException e) {
                dayPickerWrapper.setError("Invalid day");
            }
        }
    }

    private boolean validNumber(CharSequence text, Func1<Integer, Boolean> predicate) {
        return text.toString().matches("\\d+")
                && predicate.call(Integer.valueOf(text.toString()));
    }
}
