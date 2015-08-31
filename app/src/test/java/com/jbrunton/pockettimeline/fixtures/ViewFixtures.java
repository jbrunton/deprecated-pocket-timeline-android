package com.jbrunton.pockettimeline.fixtures;

import android.view.View;
import android.widget.TextView;

public class ViewFixtures {
    public static String getText(View view, int id) {
        return ((TextView) view.findViewById(id)).getText().toString();
    }
}