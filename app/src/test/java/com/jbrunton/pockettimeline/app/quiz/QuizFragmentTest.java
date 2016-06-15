package com.jbrunton.pockettimeline.app.quiz;

import android.app.Dialog;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.fixtures.FragmentTestSuite;
import com.jbrunton.pockettimeline.fixtures.TestAppRule;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;

import static com.jbrunton.pockettimeline.fixtures.AlertDialogFixtures.alertDialogButton;
import static com.jbrunton.pockettimeline.fixtures.AlertDialogFixtures.alertDialogMessage;
import static com.jbrunton.pockettimeline.fixtures.AlertDialogFixtures.alertDialogShowing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
public class QuizFragmentTest extends FragmentTestSuite<QuizFragment> {

    @Rule public final TestAppRule rule = new TestAppRule();

    @Mock QuizPresenter presenter;

    final Event EVENT = new Event.Builder()
            .id("1")
            .date(new LocalDate(2014, DateTimeConstants.JUNE, 3))
            .title("Event One")
            .description("Event One Description")
            .build();

    @Before public void setUp() {
        configureTestSuite(new QuizFragment());
        controller().start().resume();
    }

    @Test public void shouldDisplayEventDetails() {
        fragment().displayEvent(EVENT);
        assertThat(eventTitle()).isEqualTo(EVENT.getTitle());
    }

    @Test public void shouldShowCorrectDialog() {
        fragment().showCorrectDialog();
        assertThat(alertDialogMessage()).isEqualTo(getString(R.string.correct_answer));
    }

    @Test public void shouldShowIncorrectDialog() {
        fragment().showIncorrectDialog("2015");
        assertThat(alertDialogMessage()).isEqualTo(getString(R.string.incorrect_answer, "2015"));
    }

    @Test public void shouldAllowResponseDialogToBeDismissed() {
        fragment().showCorrectDialog();
        assertThat(alertDialogShowing()).isTrue();

        alertDialogButton(Dialog.BUTTON_POSITIVE).performClick();

        assertThat(alertDialogShowing()).isFalse();
    }

    @Test public void shouldAskNextQuestionAfterDialogDismissed() {
        shouldShowCorrectDialog();
        alertDialogButton(Dialog.BUTTON_POSITIVE).performClick();
        verify(presenter).nextQuestion();
    }

    @Test public void shouldClearAnswerFieldWhenDisplayingEvent() {
        submitAnswer("some answer");
        fragment().displayEvent(EVENT);
        assertThat(answer()).isEmpty();
    }

    private void submitAnswer(String answer) {
        textView(R.id.answer).setText(answer);
        button(R.id.submit).performClick();
    }

    private String eventTitle() {
        return textView(R.id.event_title).getText().toString();
    }

    private String answer() {
        return textView(R.id.answer).getText().toString();
    }
}
