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
import com.jbrunton.pockettimeline.app.search.SearchFragment;
import com.jbrunton.pockettimeline.app.shared.BaseActivity;
import com.jbrunton.pockettimeline.app.timelines.TimelinesFragment;

public class HomeActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    
    private final SparseArray<Fragment> drawerOptions = createDrawerOptions();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        configureActionBar();
        configureDrawer();

        if (savedInstanceState == null) {
            selectDrawerOption(R.id.nav_timelines);
        }
    }

    @Override protected void setupActivityComponent() {
        // nothing to inject in this class
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        setHomeAsUp(true);
    }

    private void configureDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
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
        Fragment fragment = drawerOptions.get(menuItemId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_content, fragment)
                .commit();
    }

    private SparseArray<Fragment> createDrawerOptions() {
        SparseArray<Fragment> options = new SparseArray<>();
        options.put(R.id.nav_timelines, new TimelinesFragment());
        options.put(R.id.nav_quiz, new QuizFragment());
        options.put(R.id.nav_search, new SearchFragment());
        return options;
    }
}
