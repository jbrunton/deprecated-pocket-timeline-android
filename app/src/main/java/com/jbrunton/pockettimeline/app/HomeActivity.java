package com.jbrunton.pockettimeline.app;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;

import com.jbrunton.pockettimeline.R;
import com.jbrunton.pockettimeline.app.quiz.QuizFragment;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;

public class HomeActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    
    private final SparseArray<Fragment> DRAWER_OPTIONS = new SparseArray<Fragment>() {{
        put(R.id.nav_timelines, new TimelinesFragment());
        put(R.id.nav_quiz, new QuizFragment());
    }};

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        setHomeAsUp(true);

        configureDrawer();

        if (savedInstanceState == null) {
            selectDrawerOption(R.id.nav_timelines);
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(this::onDrawerItemSelected);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer);

        drawerLayout.setDrawerListener(drawerToggle);
    }

    private boolean onDrawerItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        selectDrawerOption(menuItem.getItemId());
        return true;
    }

    private void selectDrawerOption(int menuItemId) {
        Fragment fragment = DRAWER_OPTIONS.get(menuItemId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_content, fragment)
                .commit();
    }
}
