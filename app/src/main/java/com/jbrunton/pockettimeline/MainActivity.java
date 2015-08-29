package com.jbrunton.pockettimeline;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jbrunton.pockettimeline.app.TimelinesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, new TimelinesFragment())
                .commit();
    }
}
