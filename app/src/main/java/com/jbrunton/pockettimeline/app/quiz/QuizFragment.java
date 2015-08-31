package com.jbrunton.pockettimeline.app.quiz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class QuizFragment extends BaseFragment {
    @Inject RandomHelper randomHelper;
    @Inject EventsProvider eventsProvider;

    private TextView answerField;
    private Event event;

    private List<Event> events = Arrays.asList(
            new Event("1", new LocalDate(2001, 1, 1), "Some title", "Some event")
    );

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        answerField = (TextView) view.findViewById(R.id.answer);
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onSubmitAnswer();
            }
        });

        answerField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return QuizFragment.this.onEditorAction();
            }
        });

        return view;
    }

    @Override public void onResume() {
        super.onResume();

        setTitle("Quiz");
        applicationComponent().inject(this);

        subscribeTo(eventsProvider.getEvents(), (events) -> {
            this.events = events;
            selectEvent();
        });
    }

    private void selectEvent() {
        event = events.get(randomHelper.getNext(events.size()));
        updateView(event);
    }

    private void updateView(Event event) {
        TextView eventTitle = (TextView) getView().findViewById(R.id.event_title);
        eventTitle.setText(event.getTitle());
    }

    private void onSubmitAnswer() {
        String submittedAnswer = answerField.getText().toString();
        String correctAnswer = Integer.toString(event.getDate().getYear());
        if (correctAnswer.equals(submittedAnswer)) {
            showAlert(getString(R.string.correct_answer));
        } else {
            showAlert(getString(R.string.incorrect_answer, correctAnswer));
        }
    }

    private boolean onEditorAction() {
        onSubmitAnswer();
        return true;
    }

    private void showAlert(String message) {
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                answerField.setText("");
                selectEvent();
            }
        };

        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, onClickListener)
                .create()
                .show();
    }
}
