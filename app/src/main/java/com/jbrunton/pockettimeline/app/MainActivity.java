package com.jbrunton.pockettimeline.app;

import android.os.Bundle;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, new TimelinesFragment())
                    .commit();
        }
    }
}
