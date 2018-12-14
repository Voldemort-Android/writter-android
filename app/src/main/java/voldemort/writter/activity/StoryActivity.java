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
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import voldemort.writter.R;
import voldemort.writter.fragment.StoriesFragment;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.utils.TokenUtils;

public class StoryActivity extends AppCompatActivity {


    private TextView mTitle;
    private TextView mAuthor;
    private TextView mText;
    private RatingBar mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = findViewById(R.id.title_of_story);
        mAuthor = findViewById(R.id.author_of_story);
        mText = findViewById(R.id.text_of_story);
        mText.setMovementMethod(new ScrollingMovementMethod());
        mRating = findViewById(R.id.ratingBar2);

        Intent intent = getIntent();

        mTitle.setText(intent.getStringExtra("Title"));
        mAuthor.setText(intent.getStringExtra("Author"));
        mText.setText(intent.getStringExtra("Text"));

        if (intent.getStringExtra("rating") != null) {
            int rank = Integer.parseInt(intent.getStringExtra("rating"));
            mRating.setRating(rank);
        }





//        loadStories();

    }


//    private void loadStories() {
//        StoryHttpService.getPaginatedStories(++lastPage, pageSize, mStoriesFragment::onLoadStories);
//    }
//
//    private void refreshStories() {
//        mStoriesFragment.clearStories();
//        lastPage = 0;
//        loadStories();
//    }

//    // TODO Move this somewhere else
//    private void logout() {
//
//        // Remove the stored token
//        TokenUtils.deleteToken(StoryActivity.this);
//
//        Intent intent = new Intent(StoryActivity.this, LoginActivity.class);
//
//        // Kill activity stack so that the user cannot go back using the back button.
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        startActivity(intent);
//    }

//    private void navigateTo(Class<?> activityClass) {
//        Intent intent = new Intent(StoryActivity.this, activityClass);
//        startActivity(intent);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.drawer_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        return super.onContextItemSelected(item);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.logout) {
//            logout();
//        }
//        else {
//            if (itemId == R.id.home) {
//                navigateTo(MainActivity.class);
//            }
//        }
//        mDrawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }

}
