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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import voldemort.writter.R;
import voldemort.writter.data.model.Story;
import voldemort.writter.fragment.StoriesFragment;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.utils.TokenUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final int pageSize = 20;

    private int lastPage = 0;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private StoriesFragment mStoriesFragment;

    private FloatingActionButton mNewStoryButton;

    private ScrollView mScrollView;

    private ProgressBar mProgressBar;

    private RecyclerView mRecyclerView;

    private StoryAdapter mAdapter;

    private ArrayList<Story> repos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mRecyclerView = (RecyclerView)findViewById(R.id.story_recyclerview);
        mAdapter = new StoryAdapter(this, repos);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getRecommendedStories();

//        getAllStories();


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black);
//
//        mDrawerLayout = findViewById(R.id.drawer_layout);
//
//        mNavigationView = findViewById(R.id.nav_view);
//        mNavigationView.setNavigationItemSelectedListener(this);
//        mNavigationView.setCheckedItem(R.id.home);
//
//        mStoriesFragment = (StoriesFragment) getSupportFragmentManager().findFragmentById(R.id.stories_fragment);
//
//        mNewStoryButton = findViewById(R.id.new_story_fab);
//
//        mScrollView = findViewById(R.id.scroll);
//
//        // TODO Change this to navigate to New STory activity
//        mNewStoryButton.setOnClickListener(view -> loadStories());
////        mNewStoryButton.setOnClickListener(this::logout);
//
//        loadStories();

//        AuthHttpClient.Get(
//                HttpEndpoints.WRITTER_SERVER_API + "/example/whoami",
//                (res) -> {
//                    mStoriesFragment.setText(res.getResponseBody());
//                    Log.d("HELLO", res.getResponseBody());
//                },
//                (err) -> {
//                    Log.d("HELLO", err.toString());
//                }
//        );
    }

    private void getAllStories() {
        StoryHttpService.getAllStories(this::populateRecyclerView);
    }

    private void getRecommendedStories() {
        StoryHttpService.getRecommendedStories(this::populateRecyclerView);
    }

    public void populateRecyclerView(List<Story> stories){
        if (stories != null && stories.isEmpty()) { getAllStories(); }
        mAdapter.mStories.addAll(stories);
        mAdapter.notifyDataSetChanged();
    }

    private void loadStories() {
        StoryHttpService.getPaginatedStories(++lastPage, pageSize, mStoriesFragment::onLoadStories);
    }

    private void refreshStories() {
        mStoriesFragment.clearStories();
        lastPage = 0;
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
        if (itemId == R.id.logout) {
            logout();
        }
        else {
            if (itemId == R.id.home) {
                navigateTo(MainActivity.class);
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
