package com.jbrunton.pockettimeline.app.quiz;

import com.jbrunton.pockettimeline.api.repositories.EventsRepository;
import com.jbrunton.pockettimeline.app.shared.BasePresenter;
import com.jbrunton.pockettimeline.app.shared.LoadingIndicatorView;
import com.jbrunton.pockettimeline.app.shared.SchedulerManager;
import com.jbrunton.pockettimeline.entities.models.Event;
import com.jbrunton.pockettimeline.helpers.RandomHelper;

import java.util.List;

public class QuizPresenter extends BasePresenter<QuizView> {
    private final EventsRepository repository;
    private final SchedulerManager schedulerManager;
    private final RandomHelper randomHelper;

    private Event event;
    private List<Event> events;

    public QuizPresenter(EventsRepository repository, SchedulerManager schedulerManager, RandomHelper randomHelper) {
        this.repository = repository;
        this.schedulerManager = schedulerManager;
        this.randomHelper = randomHelper;
    }

    @Override public void onResume() {
        super.onResume();
        withView(LoadingIndicatorView::showLoadingIndicator);
        repository.all()
                .compose(schedulerManager.applySchedulers())
                .subscribe(this::onEventsAvailable);
    }

    private void onEventsAvailable(List<Event> events) {
        this.events = events;
        nextQuestion();
        withView(LoadingIndicatorView::hideLoadingIndicator);
    }

    public void submitAnswer(String submittedAnswer) {
        String correctAnswer = Integer.toString(event.getDate().getYear());
        if (correctAnswer.equals(submittedAnswer)) {
            withView(QuizView::showCorrectDialog);
        } else {
            withView(view -> view.showIncorrectDialog(correctAnswer));
        }
    }

    public void nextQuestion() {
        event = events.get(randomHelper.getNext(events.size()));
        withView(view -> view.displayEvent(event));
    }
}
