package voldemort.writter.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import voldemort.writter.R;
import voldemort.writter.fragment.StoriesFragment;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.utils.TokenUtils;

public class MyStoriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean loadingStories = false;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private StoriesFragment mStoriesFragment;

    private FloatingActionButton mNewStoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black);
        actionbar.setTitle(R.string.activity_my_stories_title);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mStoriesFragment = (StoriesFragment) getSupportFragmentManager().findFragmentById(R.id.stories_fragment);
        mStoriesFragment.setNoResultsMessage(getResources().getString(R.string.no_stories));

        mNewStoryButton = findViewById(R.id.new_story_fab);
        mNewStoryButton.setOnClickListener((view) -> navigateTo(CreateStoryActivity.class, false));

        loadMyStories();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mNavigationView.setCheckedItem(R.id.navigate_my_stories);
    }

    private void loadMyStories() {
        if (loadingStories) {
            return;
        }
        loadingStories = true;
        mStoriesFragment.showProgress(true);
        StoryHttpService.getStoriesForUser((stories) -> {
            mStoriesFragment.onLoadStories(stories);
            mStoriesFragment.showProgress(false);
            loadingStories = false;
        });
    }

    private void refreshStories() {
        loadMyStories();
    }

    // TODO Move this somewhere else
    private void logout() {

        // Remove the stored token
        TokenUtils.deleteToken();

        navigateTo(LoginActivity.class, true);
    }

    private void navigateTo(Class<?> activityClass, boolean clearStack) {
        Intent intent = new Intent(this, activityClass);
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
            case R.id.action_refresh:
                refreshStories();
                return true;
            case R.id.action_back_to_top:
                mStoriesFragment.scrollToTop();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stories_option_menu, menu);
        return true;
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
            if (itemId == R.id.navigate_recommended) {
                navigateTo(RecommendationsActivity.class, false);
            }
            if (itemId == R.id.navigate_create) {
                navigateTo(CreateStoryActivity.class, false);
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
