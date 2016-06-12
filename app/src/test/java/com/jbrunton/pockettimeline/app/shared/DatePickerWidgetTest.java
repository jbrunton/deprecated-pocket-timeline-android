package com.jbrunton.pockettimeline.app.shared;

import android.support.design.widget.TextInputLayout;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.ApplicationComponent;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestApplicationModule;
import com.jbrunton.pockettimeline.fixtures.TestRepositoriesModule;
import com.jbrunton.pockettimeline.fixtures.TestRestServiceModule;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;

import javax.inject.Singleton;

import dagger.Component;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
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
        assertErrorOn(fragment().dayPickerWrapper);
    }

    @Test public void shouldNotShowErrorsOnValidInput() {
        enterDate("1", 9, "1939");

        assertValid(fragment().dayPickerWrapper);
        assertValid(fragment().yearPickerWrapper);
    }

    @Test public void shouldValidateYear() {
        enterYear("3000");
        assertErrorOn(fragment().yearPickerWrapper);
    }

    @Test public void shouldValidateDayOnceMonthAndYearGiven() {
        enterDay("31");
        assertValid(fragment().dayPickerWrapper);

        enterMonth(9);
        enterYear("1939");

        assertErrorOn(fragment().dayPickerWrapper);
    }

    @Test public void shouldNotifyWhenDateBecomesInvalid() {
        enterDate("1", 9, "1939");
        reset(listener);

        enterDay("31");

        verify(listener).onDateChanged(null);
    }

    private void assertErrorOn(TextInputLayout layout) {
        assertNotNull(layout.getError());
    }

    private void assertValid(TextInputLayout layout) {
        assertNull(layout.getError());
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

    @Singleton @Component(modules = {TestRepositoriesModule.class, TestRestServiceModule.class, TestApplicationModule.class})
    public static interface TestApplicationComponent extends ApplicationComponent {
        void inject(DatePickerWidgetTest test);
    }

    @Override protected TestApplicationComponent createComponent() {
        return DaggerDatePickerWidgetTest_TestApplicationComponent.create();
    }
}
