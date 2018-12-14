package voldemort.writter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import voldemort.writter.R;
import voldemort.writter.fragment.StoriesFragment;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.utils.TokenUtils;

public class RecommendationsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private boolean loadingStories = false;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private StoriesFragment mStoriesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black);
        actionbar.setTitle(R.string.activity_recommendations_title);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mStoriesFragment = (StoriesFragment) getSupportFragmentManager().findFragmentById(R.id.stories_fragment);
        mStoriesFragment.setNoResultsMessage(getResources().getString(R.string.no_recommendations));

        loadRecommendedStories();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mNavigationView.setCheckedItem(R.id.navigate_recommended);
    }

    private void loadRecommendedStories() {
        if (loadingStories) {
            return;
        }
        loadingStories = true;
        mStoriesFragment.showProgress(true);
        StoryHttpService.getRecommendedStories((stories) -> {
            mStoriesFragment.onLoadStories(stories);
            mStoriesFragment.showProgress(false);
            loadingStories = false;
        });
    }

    private void refreshStories() {
        loadRecommendedStories();
    }

    // TODO Move this somewhere else
    private void logout() {

        // Remove the stored token
        TokenUtils.deleteToken();

        navigateTo(LoginActivity.class, true);
    }

    private void navigateTo(Class<?> activityClass, boolean clearStack) {
        Intent intent = new Intent(RecommendationsActivity.this, activityClass);
        if (clearStack) {
            // Kill activity stack so that the user cannot go back using the back button.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.navigate_logout) {
            logout();
        }
        else {
            if (itemId == R.id.navigate_home) {
                navigateTo(MainActivity.class, true);
            }
            if (itemId == R.id.navigate_my_stories) {
                navigateTo(MyStoriesActivity.class, false);
            }
            if (itemId == R.id.navigate_create) {
                navigateTo(CreateStoryActivity.class, false);
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
