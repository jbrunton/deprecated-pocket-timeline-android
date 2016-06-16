package com.jbrunton.pockettimeline.app.shared;

import rx.functions.Action0;

public interface DisplayErrorView {
    void showMessage(String message);
    void showMessage(String text, String actionLabel, Action0 action);
}
