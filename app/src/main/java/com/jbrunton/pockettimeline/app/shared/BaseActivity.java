package com.jbrunton.pockettimeline.app.shared;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onUpPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    public void setHomeAsUp(boolean showHomeAsUp) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        actionBar.setDisplayShowHomeEnabled(showHomeAsUp);
    }

    protected void onUpPressed() {
        finish();
    }
}
