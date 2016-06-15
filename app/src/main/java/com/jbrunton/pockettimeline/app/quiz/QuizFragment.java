package com.jbrunton.pockettimeline.app.quiz;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jbrunton.pockettimeline.PerActivity;
import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.ActivityModule;
import com.jbrunton.pockettimeline.app.shared.BaseFragment;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class QuizFragment extends BaseFragment implements QuizView {
    @Inject RandomHelper randomHelper;
    @Inject @PerActivity QuizPresenter presenter;

    @BindView(R.id.answer) TextView answerField;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(presenter);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override protected void setupActivityComponent() {
        applicationComponent().activityComponent(
                new ActivityModule(getActivity())
        ).inject(this);
    }

    @Override public void onResume() {
        super.onResume();
        setTitle("Quiz");
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

    @OnEditorAction(R.id.answer)
    protected boolean onSubmitEditorAction(TextView v, int actionId, KeyEvent event) {
        submitAnswer();
        return true;
    }

    @OnClick(R.id.answer)
    protected void onSubmitClicked(View view) {
        submitAnswer();
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

}
