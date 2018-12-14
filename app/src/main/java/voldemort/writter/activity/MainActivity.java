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
import android.widget.ProgressBar;
import android.widget.ScrollView;

import voldemort.writter.R;
import voldemort.writter.fragment.StoriesFragment;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.utils.TokenUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final int pageSize = 20;

    private int lastPage = 1;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private StoriesFragment mStoriesFragment;

    private FloatingActionButton mNewStoryButton;

    private ScrollView mScrollView;

    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.navigate_home);

        mStoriesFragment = (StoriesFragment) getSupportFragmentManager().findFragmentById(R.id.stories_fragment);


        mScrollView = findViewById(R.id.scroll);

        mNewStoryButton = findViewById(R.id.new_story_fab);
        mNewStoryButton.setOnClickListener(view -> loadStories());
        mNewStoryButton.setOnClickListener((view) -> navigateTo(CreateStoryActivity.class));

        loadStories();
    }

//    private void getAllStories() {
//        StoryHttpService.getAllStories(this::populateRecyclerView);
//    }
//
//    private void getRecommendedStories() {
//        StoryHttpService.getRecommendedStories(mStoriesFragment::onLoadStories);
//    }

    private void loadStories() {
        StoryHttpService.getPaginatedStories(lastPage, pageSize, mStoriesFragment::onLoadStories);
    }

    private void loadOlderStories() {
        lastPage++;
        loadStories();
    }

    private void loadNewerStories() {
        if (lastPage <= 1) {
            return;
        }
        lastPage--;
        loadStories();
    }

    private void refreshStories() {
        lastPage = 1;
        loadStories();
    }

    // TODO Move this somewhere else
    private void logout() {

        // Remove the stored token
        TokenUtils.deleteToken(MainActivity.this);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        // Kill activity stack so that the user cannot go back using the back button.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
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
                navigateTo(MainActivity.class);
            }
            if (itemId == R.id.navigate_create) {
                navigateTo(CreateStoryActivity.class);
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
