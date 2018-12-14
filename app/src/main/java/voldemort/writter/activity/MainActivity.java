package voldemort.writter.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import voldemort.writter.R;
import voldemort.writter.fragment.StoriesFragment;
import voldemort.writter.http.HttpEndpoints;
import voldemort.writter.http.StoryHttpService;
import voldemort.writter.http.client.AuthHttpClient;
import voldemort.writter.utils.TokenUtils;

public class MainActivity extends AppCompatActivity {

    private final int pageSize = 10;

    private int lastPage = 0;

    private StoriesFragment mStoriesFragment;

    private FloatingActionButton mNewStoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStoriesFragment = (StoriesFragment) getSupportFragmentManager().findFragmentById(R.id.stories_fragment);

        mNewStoryButton = findViewById(R.id.new_story_fab);

        // TODO Change this to navigate to New STory activity
        mNewStoryButton.setOnClickListener(this::logout);

        String token = TokenUtils.getToken(MainActivity.this);

        loadStories();

    }


    private void loadStories() {

        // TODO This is supposed to load stories, not just one story.
        StoryHttpService.getStory(69, story -> mStoriesFragment.setText(story.getText()));

    }

    // TODO Move this somewhere else
    private void logout(View view) {

        // Remove the stored token
        TokenUtils.deleteToken(MainActivity.this);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        // Kill activity stack so that the user cannot go back using the back button.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

}
