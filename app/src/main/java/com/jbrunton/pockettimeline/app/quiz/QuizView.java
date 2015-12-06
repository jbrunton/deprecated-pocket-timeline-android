package com.jbrunton.pockettimeline.app.quiz;

import com.jbrunton.pockettimeline.entities.models.Event;

public interface QuizView {
    void displayEvent(Event event);
    void showCorrectDialog();
    void showIncorrectDialog(String correctAnswer);
}
