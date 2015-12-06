package com.jbrunton.pockettimeline.app.quiz;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.api.providers.EventsProvider;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.app.timelines.TimelinesPresenter;
import com.jbrunton.pockettimeline.helpers.RandomHelper;
import com.jbrunton.pockettimeline.models.Event;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class QuizFragment extends BaseFragment implements QuizView {
    @Inject RandomHelper randomHelper;
    @Inject EventsProvider eventsProvider;
    @Inject QuizPresenter presenter;

    private TextView answerField;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationComponent().inject(this);
        bind(presenter);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        answerField = (TextView) view.findViewById(R.id.answer);
        answerField.setOnEditorActionListener((v, actionId, event) -> {
            submitAnswer();
            return true;
        });
        view.findViewById(R.id.submit).setOnClickListener(v -> submitAnswer());

        return view;
    }

    @Override public void onResume() {
        super.onResume();
        setTitle("Quiz");
    }

    private void submitAnswer() {
        String submittedAnswer = answerField.getText().toString();
        presenter.submitAnswer(submittedAnswer);
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> presenter.nextQuestion())
                .create()
                .show();
    }

    @Override public void displayEvent(Event event) {
        TextView eventTitle = (TextView) getView().findViewById(R.id.event_title);
        eventTitle.setText(event.getTitle());
        answerField.setText("");
    }

    @Override public void showCorrectDialog() {
        showAlert(getString(R.string.correct_answer));
    }

    @Override public void showIncorrectDialog(String correctAnswer) {
        showAlert(getString(R.string.incorrect_answer, correctAnswer));
    }
}
