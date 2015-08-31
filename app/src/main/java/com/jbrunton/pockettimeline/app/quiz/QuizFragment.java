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
import com.jbrunton.pockettimeline.helpers.RandomHelper;
import com.jbrunton.pockettimeline.models.Event;

import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class QuizFragment extends BaseFragment {
    @Inject RandomHelper randomHelper;
    @Inject EventsProvider eventsProvider;

    private TextView answerField;
    private Event event;
    private List<Event> events;

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
        applicationComponent().inject(this);

        subscribeTo(eventsProvider.getEvents(),
                this::onEventsAvailable);
    }

    private void onEventsAvailable(List<Event> events) {
        this.events = events;
        selectEvent();
    }

    private void selectEvent() {
        event = events.get(randomHelper.getNext(events.size()));
        updateView(event);
    }

    private void updateView(Event event) {
        TextView eventTitle = (TextView) getView().findViewById(R.id.event_title);
        eventTitle.setText(event.getTitle());
        answerField.setText("");
    }

    private void submitAnswer() {
        String submittedAnswer = answerField.getText().toString();
        String correctAnswer = Integer.toString(event.getDate().getYear());
        if (correctAnswer.equals(submittedAnswer)) {
            showAlert(getString(R.string.correct_answer));
        } else {
            showAlert(getString(R.string.incorrect_answer, correctAnswer));
        }
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> selectEvent())
                .create()
                .show();
    }
}
