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

    private final SparseArray<Fragment> DRAWER_OPTIONS = new SparseArray<Fragment>();

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
                .withHeader(R.layout.nav_header_home)
                .withOnDrawerItemClickListener(this::onDrawerItemSelected)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        createDrawerOption(R.id.nav_timelines, "Timelines", new TimelinesFragment()),
                        createDrawerOption(R.id.nav_quiz, "Quiz", new QuizFragment()),
                        createDrawerOption(R.id.nav_search, "Search", new SearchFragment())
                );
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

    private PrimaryDrawerItem createDrawerOption(int identifier, String name, Fragment fragment) {
        DRAWER_OPTIONS.put(identifier, fragment);
        return new PrimaryDrawerItem()
                .withIdentifier(identifier)
                .withName(name);
    }
}
