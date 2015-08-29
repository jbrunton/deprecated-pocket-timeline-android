package com.jbrunton.pockettimeline;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jbrunton.pockettimeline.api.DaggerProvidersComponent;
import com.jbrunton.pockettimeline.api.ProvidersComponent;
import com.jbrunton.pockettimeline.api.RestService;
import com.jbrunton.pockettimeline.api.TimelinesProvider;
import com.jbrunton.pockettimeline.api.resources.TimelineResource;
import com.jbrunton.pockettimeline.models.Timeline;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends ActionBarActivity {

    final ProvidersComponent providers = DaggerProvidersComponent.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        providers.timelinesProvider().getTimelines()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Timeline>>() {
                    @Override
                    public void call(List<Timeline> timelines) {
                        Toast.makeText(MainActivity.this, "Timelines: " + timelines.size(), Toast.LENGTH_LONG).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
