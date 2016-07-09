package com.jbrunton.pockettimeline.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.quiz.QuizFragment;
import com.jbrunton.pockettimeline.app.search.SearchFragment;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class HomeActivity extends BaseActivity {

    private Drawer drawer;

    private final SparseArray<Fragment> DRAWER_OPTIONS = new SparseArray<Fragment>() {{
        put(R.id.nav_timelines, new TimelinesFragment());
        put(R.id.nav_quiz, new QuizFragment());
        put(R.id.nav_search, new SearchFragment());
    }};

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawer = buildDrawer(savedInstanceState);

        if (savedInstanceState == null) {
            drawer.setSelection(R.id.nav_timelines);
        }
    }

    @Override protected void setupActivityComponent() {

    }

    private Drawer buildDrawer(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.nav_timelines)
                                .withName("Timelines"),
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.nav_quiz)
                                .withName("Quiz"),
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.nav_search)
                                .withName("Search")
                )
                .withOnDrawerItemClickListener(this::onDrawerItemSelected)
                .withSavedInstance(savedInstanceState)
                .withHeader(R.layout.nav_header_home);
        return builder.build();
    }

    private boolean onDrawerItemSelected(View view, int position, IDrawerItem drawerItem) {
        selectDrawerOption((int) drawerItem.getIdentifier());
        drawer.closeDrawer();
        return true;
    }

    private void selectDrawerOption(int menuItemId) {
        Fragment fragment = DRAWER_OPTIONS.get(menuItemId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_content, fragment)
                .commit();
    }
}
