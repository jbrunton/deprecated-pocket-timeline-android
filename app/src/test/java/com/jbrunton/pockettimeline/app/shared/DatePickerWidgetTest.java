package com.jbrunton.pockettimeline.app.shared;

import android.app.Dialog;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.service.RestServiceModule;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.app.timelines.DaggerTimelinesFragmentTest_TestApplicationComponent;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestProvidersModule;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowDialog;

import javax.inject.Singleton;

import dagger.Component;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
public class DatePickerWidgetTest extends FragmentTestSuite<DatePickerWidget, DatePickerWidgetTest.TestApplicationComponent> {
    private DatePickerWidget.OnDateChangedListener listener;

    @Before public void setUp() {
        configureTestSuite(new DatePickerWidget());
        controller().start().resume();

        listener = mock(DatePickerWidget.OnDateChangedListener.class);

        fragment().setOnDateChangedListener(listener);
    }

    @Test public void shouldCallbackWithValidDate() {
        enterDate("1", 9, "1939");
        verify(listener).onDateChanged(new LocalDate(1939, 9, 1));
    }

    @Test public void shouldValidateDayOfMonth() {
        enterDay("200");
        assertNotNull(fragment().dayPickerWrapper.getError());
    }

    @Test public void shouldValidateYear() {
        enterYear("3000");
        assertNotNull(fragment().yearPickerWrapper.getError());
    }

    @Test public void shouldValidateDayOnceMonthAndYearGiven() {
        enterDay("31");
        assertNull(fragment().dayPickerWrapper.getError());

        enterMonth(9);
        enterYear("1939");

        assertNotNull(fragment().dayPickerWrapper.getError());
    }

    private void enterDate(String dayOfMonth, int monthOfYear, String year) {
        enterDay(dayOfMonth);
        enterMonth(monthOfYear);
        enterYear(year);
    }

    private void enterYear(String year) {
        textView(R.id.year).setText(year);
    }

    private void enterMonth(int monthOfYear) {
        textView(R.id.month_of_year).performClick();
        ShadowAlertDialog latestDialog = shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        latestDialog.clickOnItem(monthOfYear - 1);
    }

    private void enterDay(String dayOfMonth) {
        textView(R.id.day_of_month).setText(dayOfMonth);
    }

    @Singleton @Component(modules = {RestServiceModule.class, TestProvidersModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent {
        void inject(DatePickerWidgetTest test);
    }

    @Override protected TestApplicationComponent createComponent() {
        return DaggerDatePickerWidgetTest_TestApplicationComponent.create();
    }
}
